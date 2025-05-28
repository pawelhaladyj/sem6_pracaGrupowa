package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.controller;


import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto.StatsDTO;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/stats")
    public StatsDTO getStatistics() {
        log.info("API: Get library statistics");
        return reportService.generateStatistics();
    }
}
