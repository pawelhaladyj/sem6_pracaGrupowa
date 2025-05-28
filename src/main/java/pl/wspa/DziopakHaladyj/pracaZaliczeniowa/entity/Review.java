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
@Table(name = "reviews", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_user", "id_book"})
})
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_review")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_book")
    private Book book;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "review_text", columnDefinition = "TEXT")
    private String reviewText;

    @Column(name = "review_date", nullable = false)
    private LocalDateTime reviewDate;
}
