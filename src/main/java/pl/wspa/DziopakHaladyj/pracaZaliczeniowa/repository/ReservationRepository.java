package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.repository;

import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long userId);
    Optional<Reservation> findFirstByBookAndStatusOrderByReservationDateAsc(Book book, ReservationStatus status);
    Optional<Reservation> findByUserAndBookAndStatus(User user, Book book, ReservationStatus status);
    long countByBookAndStatus(Book book, ReservationStatus status);
    long countByStatus(ReservationStatus status);
}