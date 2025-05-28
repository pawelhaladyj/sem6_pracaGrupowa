package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.controller;

import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.Review;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.mapper.ReviewMapper;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/book/{bookId}/user/{userId}")
    public ReviewDTO addReview(@PathVariable Long bookId, @PathVariable Long userId, @RequestBody CreateReviewRequest request) {
        log.info("API: Add review for book {} by user {}", bookId, userId);
        Review review = reviewService.addReview(userId, bookId, request);
        return ReviewMapper.toDTO(review);
    }

    @GetMapping("/book/{bookId}")
    public List<ReviewDTO> getReviewsByBook(@PathVariable Long bookId) {
        log.info("API: Get reviews for book {}", bookId);
        List<Review> reviews = reviewService.getReviewsByBook(bookId);
        return reviews.stream().map(ReviewMapper::toDTO).collect(Collectors.toList());
    }
}