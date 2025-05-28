package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.service;

import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.exception.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public Loan borrowBook(Long userId, Long bookId) {
        log.info("User {} borrowing book {}", userId, bookId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        // Check availability and reservation queue
        if (book.getAvailableCopies() <= 0) {
            // No free copy available – ensure user is first in queue
            Reservation earliestRes = reservationRepository.findFirstByBookAndStatusOrderByReservationDateAsc(book, pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.ReservationStatus.ACTIVE)
                    .orElse(null);
            if (earliestRes == null || !earliestRes.getUser().equals(user)) {
                throw new OperationNotAllowedException("Book is not available for borrowing");
            }
        }
        // Reduce available if a free copy is taken
        if (book.getAvailableCopies() > 0) {
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            bookRepository.save(book);
        }
        // Create loan record
        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusWeeks(2));
        loan.setReturnDate(null);
        loan.setPenalty(BigDecimal.valueOf(0));
        Loan saved = loanRepository.save(loan);
        // If user had an active reservation for this book, mark it completed
        reservationRepository.findByUserAndBookAndStatus(user, book, pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.ReservationStatus.ACTIVE)
                .ifPresent(res -> {
                    res.setStatus(pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.ReservationStatus.COMPLETED);
                    reservationRepository.save(res);
                });
        log.info("Book {} borrowed by user {} (loan id={})", book.getId(), user.getId(), saved.getId());
        return saved;
    }

    public Loan returnBook(Long loanId) {
        log.info("Returning loan {}", loanId);
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));
        if (loan.getReturnDate() != null) {
            return loan;
        }
        loan.setReturnDate(LocalDate.now());
        // Calculate fine if overdue
        if (loan.getReturnDate().isAfter(loan.getDueDate())) {
            long daysLate = java.time.temporal.ChronoUnit.DAYS.between(loan.getDueDate(), loan.getReturnDate());
            loan.setPenalty(BigDecimal.valueOf(daysLate));
        }
        Loan savedLoan = loanRepository.save(loan);
        Book book = loan.getBook();
        // Check if any reservations waiting for this book
        long activeLoans = loanRepository.countByBookAndReturnDateIsNull(book);
        long activeResCount = reservationRepository.countByBookAndStatus(book, pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.ReservationStatus.ACTIVE);
        long reservedAsideCount = book.getTotalCopies() - book.getAvailableCopies() - activeLoans;
        if (activeResCount > reservedAsideCount) {
            // There are waiting reservations – notify next user (book remains reserved)
            log.info("Notifying next user in queue for book {}", book.getId());
        } else {
            // No waiting reservations – increment available copies
            book.setAvailableCopies(book.getAvailableCopies() + 1);
            bookRepository.save(book);
        }
        log.info("Book {} returned (loan id={}). Fine: {}", book.getId(), loanId, savedLoan.getPenalty());
        return savedLoan;
    }

    public List<Loan> getLoansByUser(Long userId) {
        log.info("Fetching loans for user {}", userId);
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return loanRepository.findByUserId(userId);
    }

    public List<Loan> getAllLoans() {
        log.info("Fetching all loans");
        return loanRepository.findAll();
    }
}