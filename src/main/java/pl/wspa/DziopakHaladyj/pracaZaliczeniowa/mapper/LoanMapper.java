package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.mapper;


import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto.LoanDTO;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.Loan;

public class LoanMapper {
    public static LoanDTO toDTO(Loan loan) {
        if (loan == null) return null;
        LoanDTO dto = new LoanDTO();
        dto.setId(loan.getId());
        dto.setBookId(loan.getBook().getId());
        dto.setBookTitle(loan.getBook().getTitle());
        dto.setUserId(loan.getUser().getId());
        dto.setUserName(loan.getUser().getFirstName() + " " + loan.getUser().getLastName());
        dto.setLoanDate(loan.getLoanDate());
        dto.setDueDate(loan.getDueDate());
        dto.setReturnDate(loan.getReturnDate());
        dto.setPenalty(loan.getPenalty());
        return dto;
    }
}
