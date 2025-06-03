package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.controller;

import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.Book;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.mapper.BookMapper;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<BookDTO>> getBooks(
            @RequestHeader("authToken") String authToken,
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer year) {
        log.info("API: Get books (authToken={}, query={}, genre={}, year={})", authToken, query, genre, year);
        List<Book> books = bookService.getAllBooks(query, genre, year);
        List<BookDTO> dtos = books.stream().map(BookMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(
            @RequestHeader("authToken") String authToken,
            @PathVariable Long id) {
        log.info("API: Get book by id (authToken={}, id={})", authToken, id);
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(BookMapper.toDTO(book));
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(
            @RequestHeader("authToken") String authToken,
            @RequestBody CreateBookRequest request) {
        log.info("API: Create new book (authToken={})", authToken);
        Book book = bookService.addBook(request);
        return ResponseEntity.ok(BookMapper.toDTO(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(
            @RequestHeader("authToken") String authToken,
            @PathVariable Long id,
            @RequestBody CreateBookRequest request) {
        log.info("API: Update book (authToken={}, id={})", authToken, id);
        Book book = bookService.updateBook(id, request);
        return ResponseEntity.ok(BookMapper.toDTO(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(
            @RequestHeader("authToken") String authToken,
            @PathVariable Long id) {
        log.info("API: Delete book (authToken={}, id={})", authToken, id);
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully");
    }
}
