package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto;

import lombok.Data;

@Data
public class BorrowRequest {
    private Long userId;
    private Long bookId;
}
