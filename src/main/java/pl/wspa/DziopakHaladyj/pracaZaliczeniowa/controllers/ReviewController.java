package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.models.dto.RatingDto;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.models.dto.ReviewDto;

// Kontroler obsługujący operacje w domenie opinii i ocen:
// UC-15: Add Book Review
// UC-16: Rate Book

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    // UC-15: Add Book Review
    // Endpoint umożliwiający dodanie opinii o książce.
    @PostMapping("/add")
    public ResponseEntity<String> addReview(@RequestBody ReviewDto reviewDto) {
        // Logika dodawania opinii (np. zapis w bazie danych)
        return ResponseEntity.ok("Review added successfully for book " + reviewDto.getBookId());
    }

    // UC-16: Rate Book
    // Endpoint umożliwiający ocenę książki (w skali 1-5).
    @PostMapping("/rate")
    public ResponseEntity<String> rateBook(@RequestBody RatingDto ratingDto) {
        // Logika przypisania oceny do książki (np. zapis oceny w bazie danych)
        return ResponseEntity.ok("Book " + ratingDto.getBookId() + " rated with "
                + ratingDto.getRating() + " stars.");
    }

    // Opcjonalnie: Endpoint pobierający opinie dla danej książki
    @GetMapping("/{bookId}")
    public ResponseEntity<String> getReviewsForBook(@PathVariable Long bookId) {
        // Logika pobierania wszystkich opinii dla danego ID książki
        return ResponseEntity.ok("Reviews for book " + bookId + " displayed.");
    }
}
