package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.controller;

import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.Loan;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.mapper.LoanMapper;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;

    @PostMapping
    public LoanDTO borrowBook(@RequestBody BorrowRequest request) {
        log.info("API: Borrow book {} by user {}", request.getBookId(), request.getUserId());
        Loan loan = loanService.borrowBook(request.getUserId(), request.getBookId());
        return LoanMapper.toDTO(loan);
    }

    @PostMapping("/{id}/return")
    public LoanDTO returnBook(@PathVariable Long id) {
        log.info("API: Return loan {}", id);
        Loan loan = loanService.returnBook(id);
        return LoanMapper.toDTO(loan);
    }

    @GetMapping("/user/{userId}")
    public List<LoanDTO> getLoansByUser(@PathVariable Long userId) {
        log.info("API: Get loans for user {}", userId);
        List<Loan> loans = loanService.getLoansByUser(userId);
        return loans.stream().map(LoanMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping
    public List<LoanDTO> getAllLoans() {
        log.info("API: Get all loans");
        List<Loan> loans = loanService.getAllLoans();
        return loans.stream().map(LoanMapper::toDTO).collect(Collectors.toList());
    }
}
