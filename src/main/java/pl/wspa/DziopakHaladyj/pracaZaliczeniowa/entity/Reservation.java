package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(exclude = {"user", "book"})
@ToString(exclude = {"user", "book"})
@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_book")
    private Book book;

    @Column(name = "reservation_date", nullable = false)
    private LocalDateTime reservationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ReservationStatus status;
}