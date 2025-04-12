package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.models.dto;

import lombok.Data;

@Data
public class LateFeeDto {
    private Long userId;
    private Long bookId;
    private int daysOverdue; // Liczba dni opóźnienia
}
