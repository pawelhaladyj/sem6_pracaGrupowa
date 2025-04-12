package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.models.dto;

import lombok.Data;

@Data
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String genre;
    private int publishedYear;
    private int availableCopies;
    private int totalCopies;
}
