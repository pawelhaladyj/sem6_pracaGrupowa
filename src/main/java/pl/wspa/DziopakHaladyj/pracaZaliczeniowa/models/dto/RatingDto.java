package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.models.dto;

import lombok.Data;

import java.util.Objects;

public class RatingDto {
    private Long userId;
    private Long bookId;
    private int rating; // Ocena w skali od 1 do 5

    public RatingDto() {
    }

    public RatingDto(Long userId, Long bookId, int rating) {
        this.userId = userId;
        this.bookId = bookId;
        this.rating = rating;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingDto ratingDto = (RatingDto) o;
        return rating == ratingDto.rating && Objects.equals(userId, ratingDto.userId) && Objects.equals(bookId, ratingDto.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, bookId, rating);
    }

    @Override
    public String toString() {
        return "RatingDto{" +
                "userId=" + userId +
                ", bookId=" + bookId +
                ", rating=" + rating +
                '}';
    }
}
