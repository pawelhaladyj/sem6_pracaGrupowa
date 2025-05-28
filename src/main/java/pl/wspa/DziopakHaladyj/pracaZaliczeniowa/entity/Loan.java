package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(exclude = {"user", "book"})
@ToString(exclude = {"user", "book"})
@Entity
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_loan")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_book")
    private Book book;

    @Column(name = "loan_date", nullable = false)
    private LocalDate loanDate;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "penalty", nullable = false)
    private BigDecimal penalty;
}