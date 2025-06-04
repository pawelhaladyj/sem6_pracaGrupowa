package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.controller;

import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.Loan;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.mapper.LoanMapper;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class LoanController {
    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<LoanDTO> borrowBook(
            @RequestHeader("authToken") String authToken,
            @RequestBody BorrowRequest request) {
        log.info("API: Borrow book (authToken={}, bookId={}, userId={})", authToken, request.getBookId(), request.getUserId());
        Loan loan = loanService.borrowBook(request.getUserId(), request.getBookId());
        return ResponseEntity.ok(LoanMapper.toDTO(loan));
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<LoanDTO> returnBook(
            @RequestHeader("authToken") String authToken,
            @PathVariable Long id) {
        log.info("API: Return loan (authToken={}, loanId={})", authToken, id);
        Loan loan = loanService.returnBook(id);
        return ResponseEntity.ok(LoanMapper.toDTO(loan));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LoanDTO>> getLoansByUser(
            @RequestHeader("authToken") String authToken,
            @PathVariable Long userId) {
        log.info("API: Get loans for user (authToken={}, userId={})", authToken, userId);
        List<Loan> loans = loanService.getLoansByUser(userId);
        List<LoanDTO> dtos = loans.stream().map(LoanMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping
    public ResponseEntity<List<LoanDTO>> getAllLoans(
            @RequestHeader("authToken") String authToken) {
        log.info("API: Get all loans (authToken={})", authToken);
        List<Loan> loans = loanService.getAllLoans();
        List<LoanDTO> dtos = loans.stream().map(LoanMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
