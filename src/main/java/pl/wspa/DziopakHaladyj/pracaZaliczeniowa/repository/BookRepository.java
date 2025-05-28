package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);
    List<Book> findByGenreIgnoreCase(String genre);
    List<Book> findByPublishedYear(Integer year);
}
