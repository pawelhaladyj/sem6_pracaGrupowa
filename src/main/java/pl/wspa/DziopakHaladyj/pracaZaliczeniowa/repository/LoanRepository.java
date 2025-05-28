package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.Book;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.Loan;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.User;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUserId(Long userId);
    boolean existsByUser(User user);
    boolean existsByUserAndReturnDateIsNull(User user);
    boolean existsByBook(Book book);
    boolean existsByBookAndReturnDateIsNull(Book book);
    long countByReturnDateIsNull();
    long countByBookAndReturnDateIsNull(Book book);
    long countByReturnDateIsNullAndDueDateBefore(LocalDate date);
}
