package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.models.dto.LoanReportDto;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    // UC-13: Generate Loan Report
    // Endpoint generuje raport wypożyczeń w zadanym przedziale czasowym.
    @GetMapping("/loans")
    public ResponseEntity<LoanReportDto> generateLoanReport(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        // W rzeczywistej aplikacji wywołana zostanie warstwa serwisowa, która pobierze
        // odpowiednie dane z bazy i wygeneruje raport.
        LoanReportDto report = new LoanReportDto();
        report.setReportTitle("Loan Report");
        report.setContent("Generated loan report for the period: " + startDate + " to " + endDate);
        return ResponseEntity.ok(report);
    }
}
