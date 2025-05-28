package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReviewDTO {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private Long userId;
    private String userName;
    private Integer rating;
    private String reviewText;
    private LocalDateTime reviewDate;
}
