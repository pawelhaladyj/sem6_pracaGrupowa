package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.models.dto.LateFeeDto;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.models.dto.LoanDto;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.models.dto.ReservationDto;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.models.dto.ReturnDto;

// Kontroler obsługujący operacje w domenie rezerwacji i wypożyczeń:
// UC-6: Reserve Book
// UC-7: Loan Book
// UC-8: Return Book
// UC-9: Send Return Reminder
// UC-23: Calculate Late Fee
// UC-24: View Loan History

@RestController
@RequestMapping("/api/reservations-loans")
public class ReservationLoanController {

    // UC-6: Reserve Book
    @PostMapping("/reserve")
    public ResponseEntity<String> reserveBook(@RequestBody ReservationDto reservationDto) {
        // Logika rezerwacji książki (przypisanie rezerwacji do użytkownika i książki)
        return ResponseEntity.ok("Book reserved successfully.");
    }

    // UC-7: Loan Book
    @PostMapping("/loan")
    public ResponseEntity<String> loanBook(@RequestBody LoanDto loanDto) {
        // Logika wypożyczenia książki (potwierdzenie rezerwacji i utworzenie wpisu wypożyczenia)
        return ResponseEntity.ok("Book loaned successfully.");
    }

    // UC-8: Return Book
    @PostMapping("/return")
    public ResponseEntity<String> returnBook(@RequestBody ReturnDto returnDto) {
        // Logika zwrotu książki (aktualizacja statusu wypożyczenia oraz przyjmowanie zwrotu)
        return ResponseEntity.ok("Book returned successfully.");
    }

    // UC-9: Send Return Reminder
    @PostMapping("/send-reminder")
    public ResponseEntity<String> sendReturnReminder() {
        // Logika wysyłania przypomnienia o zwrocie książki (np. inicjacja wysyłki e-mail)
        return ResponseEntity.ok("Return reminder sent.");
    }

    // UC-23: Calculate Late Fee
    @PostMapping("/calculate-late-fee")
    public ResponseEntity<String> calculateLateFee(@RequestBody LateFeeDto lateFeeDto) {
        // Logika naliczania kar za opóźnienia (obliczenie kwoty kary na podstawie liczby dni opóźnienia)
        return ResponseEntity.ok("Late fee calculated.");
    }

    // UC-24: View Loan History
    @GetMapping("/history/{userId}")
    public ResponseEntity<String> viewLoanHistory(@PathVariable Long userId) {
        // Logika pobierania historii wypożyczeń użytkownika
        return ResponseEntity.ok("Loan history for user: " + userId);
    }
}
