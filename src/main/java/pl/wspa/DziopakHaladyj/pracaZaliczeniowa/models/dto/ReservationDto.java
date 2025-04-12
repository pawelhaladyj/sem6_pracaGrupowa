package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.models.dto;

import lombok.Data;

@Data
public class ReservationDto {
    private Long userId;
    private Long bookId;
    private String reservationDate; // Format np. "yyyy-MM-dd"
}
