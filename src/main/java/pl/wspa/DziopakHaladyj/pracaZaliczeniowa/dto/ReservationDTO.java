package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReservationDTO {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private Long userId;
    private String userName;
    private LocalDateTime reservationDate;
    private String status;
}
