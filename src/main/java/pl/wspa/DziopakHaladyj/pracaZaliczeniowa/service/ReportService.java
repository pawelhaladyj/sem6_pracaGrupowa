package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.service;

import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReportService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final ReservationRepository reservationRepository;
    private final NewsletterSubscriptionRepository subscriptionRepository;

    public StatsDTO generateStatistics() {
        log.info("Generating library statistics");
        StatsDTO stats = new StatsDTO();
        stats.setTotalUsers(userRepository.count());
        stats.setTotalBooks(bookRepository.count());
        stats.setTotalLoans(loanRepository.count());
        stats.setActiveLoans(loanRepository.countByReturnDateIsNull());
        stats.setActiveReservations(reservationRepository.countByStatus(pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.ReservationStatus.ACTIVE));
        stats.setTotalSubscribers(subscriptionRepository.count());
        stats.setOverdueLoans(loanRepository.countByReturnDateIsNullAndDueDateBefore(LocalDate.now()));
        return stats;
    }
}
