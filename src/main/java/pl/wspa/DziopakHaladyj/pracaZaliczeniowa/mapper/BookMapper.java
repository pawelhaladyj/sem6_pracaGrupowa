package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.mapper;


import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto.BookDTO;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.Book;

public class BookMapper {
    public static BookDTO toDTO(Book book) {
        if (book == null) return null;
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setGenre(book.getGenre());
        dto.setPublishedYear(book.getPublishedYear());
        dto.setAvailableCopies(book.getAvailableCopies());
        dto.setTotalCopies(book.getTotalCopies());
        dto.setCoverImageUrl(book.getCoverImageUrl());
        return dto;
    }

    public static Book toEntity(BookDTO dto) {
        if (dto == null) return null;
        Book book = new Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setGenre(dto.getGenre());
        book.setPublishedYear(dto.getPublishedYear());
        book.setAvailableCopies(dto.getAvailableCopies());
        book.setTotalCopies(dto.getTotalCopies());
        book.setCoverImageUrl(dto.getCoverImageUrl());
        return book;
    }
}