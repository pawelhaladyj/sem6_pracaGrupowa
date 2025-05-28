package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.service;


import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto.CreateBookRequest;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.Book;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.exception.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final NewsletterService newsletterService;

    public List<Book> getAllBooks(String query, String genre, Integer year) {
        log.info("Fetching books with query: {}, genre: {}, year: {}", query, genre, year);
        List<Book> results;
        if (genre != null && !genre.isEmpty()) {
            // Filter by genre first
            results = bookRepository.findByGenreIgnoreCase(genre);
            if (query != null && !query.isEmpty()) {
                String q = query.toLowerCase();
                // Filter further by query on title or author
                results.removeIf(book -> !(book.getTitle().toLowerCase().contains(q)
                        || book.getAuthor().toLowerCase().contains(q)));
            }
        } else if (query != null && !query.isEmpty()) {
            results = bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query);
        } else if (year != null) {
            results = bookRepository.findByPublishedYear(year);
        } else {
            results = bookRepository.findAll();
        }
        log.info("Found {} books", results.size());
        return results;
    }

    public Book getBookById(Long bookId) {
        log.info("Fetching book by id: {}", bookId);
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    public Book addBook(CreateBookRequest request) {
        log.info("Adding new book: {}", request.getTitle());
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setGenre(request.getGenre());
        book.setPublishedYear(request.getPublishedYear());
        book.setTotalCopies(request.getTotalCopies() != null ? request.getTotalCopies() : 1);
        book.setAvailableCopies(book.getTotalCopies());
        book.setCoverImageUrl(request.getCoverImageUrl());
        Book saved = bookRepository.save(book);
        log.info("Book added: {} (id={})", saved.getTitle(), saved.getId());
        // Notify subscribers about new book
        newsletterService.notifyNewBook(saved);
        return saved;
    }

    public Book updateBook(Long bookId, CreateBookRequest request) {
        log.info("Updating book id: {}", bookId);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        // Update fields
        if (request.getTitle() != null) book.setTitle(request.getTitle());
        if (request.getAuthor() != null) book.setAuthor(request.getAuthor());
        if (request.getGenre() != null) book.setGenre(request.getGenre());
        if (request.getPublishedYear() != null) book.setPublishedYear(request.getPublishedYear());
        if (request.getTotalCopies() != null) {
            int newTotal = request.getTotalCopies();
            int currentTotal = book.getTotalCopies();
            int onLoan = currentTotal - book.getAvailableCopies();
            if (newTotal < onLoan) {
                throw new OperationNotAllowedException("Cannot reduce total copies below copies currently on loan");
            }
            if (newTotal < currentTotal) {
                int toRemove = currentTotal - newTotal;
                if (toRemove > book.getAvailableCopies()) {
                    toRemove = book.getAvailableCopies();
                }
                book.setAvailableCopies(book.getAvailableCopies() - toRemove);
            } else if (newTotal > currentTotal) {
                int toAdd = newTotal - currentTotal;
                book.setAvailableCopies(book.getAvailableCopies() + toAdd);
            }
            book.setTotalCopies(newTotal);
        }
        if (request.getCoverImageUrl() != null) book.setCoverImageUrl(request.getCoverImageUrl());
        Book updated = bookRepository.save(book);
        log.info("Book updated: {} (id={})", updated.getTitle(), updated.getId());
        return updated;
    }

    public void deleteBook(Long bookId) {
        log.info("Deleting book id: {}", bookId);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        if (loanRepository.existsByBookAndReturnDateIsNull(book)) {
            throw new OperationNotAllowedException("Book is currently on loan and cannot be deleted");
        }
        if (loanRepository.existsByBook(book)) {
            throw new OperationNotAllowedException("Book has loan history and cannot be deleted");
        }
        bookRepository.delete(book);
        log.info("Book deleted: {} (id={})", book.getTitle(), book.getId());
    }
}
