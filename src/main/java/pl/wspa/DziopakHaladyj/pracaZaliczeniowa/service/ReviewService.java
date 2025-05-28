package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.service;

import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.exception.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public Review addReview(Long userId, Long bookId, CreateReviewRequest request) {
        log.info("User {} adding review for book {}", userId, bookId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        if (reviewRepository.existsByUserAndBook(user, book)) {
            throw new DuplicateResourceException("User has already reviewed this book");
        }
        if (request.getRating() == null || request.getRating() < 1 || request.getRating() > 5) {
            throw new ResourceNotFoundException("Rating must be between 1 and 5");
        }
        Review review = new Review();
        review.setUser(user);
        review.setBook(book);
        review.setRating(request.getRating());
        review.setReviewText(request.getReviewText());
        review.setReviewDate(LocalDateTime.now());
        Review saved = reviewRepository.save(review);
        log.info("Review added (id={}): user {} -> book {}", saved.getId(), user.getId(), book.getId());
        return saved;
    }

    public List<Review> getReviewsByBook(Long bookId) {
        log.info("Fetching reviews for book {}", bookId);
        bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        return reviewRepository.findByBookId(bookId);
    }
}