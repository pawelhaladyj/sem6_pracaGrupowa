package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto;

import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String genre;
    private Integer publishedYear;
    private Integer availableCopies;
    private Integer totalCopies;
    private String coverImageUrl;
}