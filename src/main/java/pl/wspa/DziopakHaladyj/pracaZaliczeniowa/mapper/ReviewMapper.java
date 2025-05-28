package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.mapper;


import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto.ReviewDTO;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.Review;

public class ReviewMapper {
    public static ReviewDTO toDTO(Review review) {
        if (review == null) return null;
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setBookId(review.getBook().getId());
        dto.setBookTitle(review.getBook().getTitle());
        dto.setUserId(review.getUser().getId());
        dto.setUserName(review.getUser().getFirstName() + " " + review.getUser().getLastName());
        dto.setRating(review.getRating());
        dto.setReviewText(review.getReviewText());
        dto.setReviewDate(review.getReviewDate());
        return dto;
    }
}