package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.models.dto;


import lombok.Data;

@Data
public class ReturnDto {
    private Long userId;
    private Long bookId;
    private String returnDate; // Format np. "yyyy-MM-dd"
}
