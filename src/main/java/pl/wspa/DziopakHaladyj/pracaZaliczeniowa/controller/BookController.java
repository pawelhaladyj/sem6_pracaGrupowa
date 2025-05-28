package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.controller;

import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.Book;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.mapper.BookMapper;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDTO> getBooks(@RequestParam(required = false) String query,
                                  @RequestParam(required = false) String genre,
                                  @RequestParam(required = false) Integer year) {
        log.info("API: Get books (query={}, genre={}, year={})", query, genre, year);
        List<Book> books = bookService.getAllBooks(query, genre, year);
        return books.stream().map(BookMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookDTO getBookById(@PathVariable Long id) {
        log.info("API: Get book by id {}", id);
        Book book = bookService.getBookById(id);
        return BookMapper.toDTO(book);
    }

    @PostMapping
    public BookDTO createBook(@RequestBody CreateBookRequest request) {
        log.info("API: Create new book");
        Book book = bookService.addBook(request);
        return BookMapper.toDTO(book);
    }

    @PutMapping("/{id}")
    public BookDTO updateBook(@PathVariable Long id, @RequestBody CreateBookRequest request) {
        log.info("API: Update book {}", id);
        Book book = bookService.updateBook(id, request);
        return BookMapper.toDTO(book);
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {
        log.info("API: Delete book {}", id);
        bookService.deleteBook(id);
        return "Book deleted successfully";
    }
}