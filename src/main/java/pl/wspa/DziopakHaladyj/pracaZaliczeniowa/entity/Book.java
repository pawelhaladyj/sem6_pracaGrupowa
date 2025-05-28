package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = {"reservations", "loans", "reviews"})
@ToString(exclude = {"reservations", "loans", "reviews"})
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_book")
    private Long id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "author", nullable = false, length = 255)
    private String author;

    @Column(name = "genre", length = 50)
    private String genre;

    @Column(name = "published_year")
    private Integer publishedYear;

    @Column(name = "available_copies", nullable = false)
    private Integer availableCopies;

    @Column(name = "total_copies", nullable = false)
    private Integer totalCopies;

    @Column(name = "cover_url")
    private String coverImageUrl;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<Loan> loans = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();
}
