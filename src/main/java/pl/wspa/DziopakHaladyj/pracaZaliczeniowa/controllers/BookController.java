package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.models.dto.BookDto;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.models.dto.CoverDto;

// Kontroler obsługujący operacje w domenie książek:
// UC-4: View Book Catalog
// UC-5: Search Books
// UC-10: Add New Book
// UC-11: Delete Book
// UC-12: Update Book Details
// UC-19: View Book Details
// UC-20: Filter Books By Genre
// UC-21: Upload Book Cover
// UC-25: Update Book Availability
// UC-26: Import Books Data
// UC-27: Export Books Data

@RestController
@RequestMapping("/api/books")
public class BookController {

    // UC-4: View Book Catalog
    @GetMapping("/catalog")
    public ResponseEntity<String> viewCatalog() {
        // Logika pobrania pełnego katalogu książek
        return ResponseEntity.ok("Book catalog displayed.");
    }

    // UC-5: Search Books
    @GetMapping("/search")
    public ResponseEntity<String> searchBooks(@RequestParam String query) {
        // Logika wyszukiwania książek według podanego zapytania
        return ResponseEntity.ok("Search results for query: " + query);
    }

    // UC-10: Add New Book
    @PostMapping("/add")
    public ResponseEntity<String> addBook(@RequestBody BookDto bookDto) {
        // Logika dodawania nowej książki do katalogu
        return ResponseEntity.ok("Book added successfully.");
    }

    // UC-11: Delete Book
    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable Long bookId) {
        // Logika usuwania książki z katalogu na podstawie ID
        return ResponseEntity.ok("Book deleted successfully.");
    }

    // UC-12: Update Book Details
    @PutMapping("/update/{bookId}")
    public ResponseEntity<String> updateBook(@PathVariable Long bookId, @RequestBody BookDto bookDto) {
        // Logika aktualizacji danych książki
        return ResponseEntity.ok("Book updated successfully.");
    }

    // UC-19: View Book Details
    @GetMapping("/details/{bookId}")
    public ResponseEntity<String> viewBookDetails(@PathVariable Long bookId) {
        // Logika pobrania szczegółowych informacji o książce
        return ResponseEntity.ok("Details of book " + bookId + " displayed.");
    }

    // UC-20: Filter Books By Genre
    @GetMapping("/filter")
    public ResponseEntity<String> filterBooksByGenre(@RequestParam String genre) {
        // Logika filtrowania książek według gatunku
        return ResponseEntity.ok("Books filtered by genre: " + genre);
    }

    // UC-21: Upload Book Cover
    @PostMapping("/{bookId}/cover")
    public ResponseEntity<String> uploadBookCover(@PathVariable Long bookId, @RequestBody CoverDto coverDto) {
        // Logika przesyłania zdjęcia okładki książki
        return ResponseEntity.ok("Book cover uploaded successfully for book " + bookId);
    }

    // UC-25: Update Book Availability
    @PutMapping("/availability/{bookId}")
    public ResponseEntity<String> updateBookAvailability(@PathVariable Long bookId, @RequestParam boolean available) {
        // Logika aktualizacji dostępności książki (np. ustawienie jako dostępna lub niedostępna)
        return ResponseEntity.ok("Book availability updated successfully for book " + bookId);
    }

    // UC-26: Import Books Data
    @PostMapping("/import")
    public ResponseEntity<String> importBooks() {
        // Logika importu danych książek, np. z pliku CSV lub XML
        return ResponseEntity.ok("Books data imported successfully.");
    }

    // UC-27: Export Books Data
    @GetMapping("/export")
    public ResponseEntity<String> exportBooks() {
        // Logika eksportu danych książek, np. do pliku CSV lub XML
        return ResponseEntity.ok("Books data exported successfully.");
    }
}
