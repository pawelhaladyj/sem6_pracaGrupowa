package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.models.dto;

import java.util.Objects;

public class ReviewDto {
    private Long userId;
    private Long bookId;
    private String reviewText; // Tekst opinii

    public ReviewDto(Long userId, Long bookId, String reviewText) {
        this.userId = userId;
        this.bookId = bookId;
        this.reviewText = reviewText;
    }

    public ReviewDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewDto reviewDto = (ReviewDto) o;
        return Objects.equals(userId, reviewDto.userId) && Objects.equals(bookId, reviewDto.bookId) && Objects.equals(reviewText, reviewDto.reviewText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, bookId, reviewText);
    }

    @Override
    public String toString() {
        return "ReviewDto{" +
                "userId=" + userId +
                ", bookId=" + bookId +
                ", reviewText='" + reviewText + '\'' +
                '}';
    }
}


