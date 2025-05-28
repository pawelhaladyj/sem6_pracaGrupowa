package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto;

import lombok.Data;

@Data
public class CreateBookRequest {
    private String title;
    private String author;
    private String genre;
    private Integer publishedYear;
    private Integer totalCopies;
    private String coverImageUrl;
}