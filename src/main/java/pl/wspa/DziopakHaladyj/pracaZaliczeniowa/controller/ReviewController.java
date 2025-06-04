package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.controller;

import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.Review;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.mapper.ReviewMapper;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/book/{bookId}/user/{userId}")
    public ResponseEntity<ReviewDTO> addReview(
            @RequestHeader("authToken") String authToken,
            @PathVariable Long bookId,
            @PathVariable Long userId,
            @RequestBody CreateReviewRequest request) {
        log.info("API: Add review (authToken={}, bookId={}, userId={})", authToken, bookId, userId);
        Review review = reviewService.addReview(userId, bookId, request);
        return ResponseEntity.ok(ReviewMapper.toDTO(review));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByBook(
            @RequestHeader("authToken") String authToken,
            @PathVariable Long bookId) {
        log.info("API: Get reviews for book (authToken={}, bookId={})", authToken, bookId);
        List<Review> reviews = reviewService.getReviewsByBook(bookId);
        List<ReviewDTO> dtos = reviews.stream()
                .map(ReviewMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
