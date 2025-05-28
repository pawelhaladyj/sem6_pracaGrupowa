package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LoanDTO {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private Long userId;
    private String userName;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private BigDecimal penalty;
}
