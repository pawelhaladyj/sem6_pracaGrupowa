package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto;

import lombok.Data;

@Data
public class ReserveRequest {
    private Long userId;
    private Long bookId;
}