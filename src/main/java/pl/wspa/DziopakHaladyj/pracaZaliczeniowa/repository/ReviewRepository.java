package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.repository;

import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBookId(Long bookId);
    List<Review> findByUserId(Long userId);
    Optional<Review> findByUserAndBook(User user, Book book);
    boolean existsByUserAndBook(User user, Book book);
}
