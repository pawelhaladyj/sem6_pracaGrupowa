package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.service;

import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.exception.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    public Reservation reserveBook(Long userId, Long bookId) {
        log.info("User {} reserving book {}", userId, bookId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        // Check if user already has an active reservation for this book
        if (reservationRepository.findByUserAndBookAndStatus(user, book, ReservationStatus.ACTIVE).isPresent()) {
            throw new OperationNotAllowedException("You have already reserved this book");
        }
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setBook(book);
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.ACTIVE);
        // If book is available, allocate a copy
        if (book.getAvailableCopies() > 0) {
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            bookRepository.save(book);
            log.info("Allocated a copy of book {} for reservation", book.getId());
            // In a real system, notify user that book is ready for pickup
            log.info("Notifying user {} that book '{}' is ready for pickup", user.getId(), book.getTitle());
        }
        Reservation saved = reservationRepository.save(reservation);
        log.info("Reservation created (id={}): user {} -> book {}", saved.getId(), user.getId(), book.getId());
        return saved;
    }

    public List<Reservation> getReservationsByUser(Long userId) {
        log.info("Fetching reservations for user {}", userId);
        return reservationRepository.findByUserId(userId);
    }

    public void cancelReservation(Long reservationId) {
        log.info("Cancelling reservation {}", reservationId);
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        if (reservation.getStatus() != ReservationStatus.ACTIVE) {
            return;
        }
        Book book = reservation.getBook();
        // Determine if this reservation had an allocated copy
        List<Reservation> allRes = reservationRepository
                .findFirstByBookAndStatusOrderByReservationDateAsc(book, ReservationStatus.ACTIVE)
                .map(List::of).orElse(List.of());;
        int reservedCount = book.getTotalCopies() - book.getAvailableCopies() - (int)loanRepository.countByBookAndReturnDateIsNull(book);
        boolean hadCopy = allRes.indexOf(reservation) < reservedCount;
        // Remove reservation (mark as cancelled)
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
        if (hadCopy) {
            // If someone else is waiting, do not free the copy but allocate to next
            if (allRes.size() > reservedCount) {
                // Notify next waiting user
                Reservation nextRes = allRes.get(reservedCount);
                log.info("Notifying user {} that book '{}' is now available for them", nextRes.getUser().getId(), book.getTitle());
            } else {
                // No one waiting, free the copy
                book.setAvailableCopies(book.getAvailableCopies() + 1);
                bookRepository.save(book);
            }
        }
        log.info("Reservation {} cancelled", reservationId);
    }
}