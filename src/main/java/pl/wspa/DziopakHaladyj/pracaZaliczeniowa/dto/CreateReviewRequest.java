package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto;

import lombok.Data;

@Data
public class CreateReviewRequest {
    private Integer rating;
    private String reviewText;
}

