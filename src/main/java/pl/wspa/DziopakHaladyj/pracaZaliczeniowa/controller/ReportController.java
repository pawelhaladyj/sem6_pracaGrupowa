package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.controller;

import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto.StatsDTO;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/stats")
    public ResponseEntity<StatsDTO> getStatistics(
            @RequestHeader("authToken") String authToken) {
        log.info("API: Get library statistics (authToken={})", authToken);
        StatsDTO stats = reportService.generateStatistics();
        return ResponseEntity.ok(stats);
    }
}
