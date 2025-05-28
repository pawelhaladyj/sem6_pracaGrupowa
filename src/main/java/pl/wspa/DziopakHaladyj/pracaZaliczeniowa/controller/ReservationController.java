package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.controller;

import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.Reservation;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.mapper.ReservationMapper;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ReservationDTO reserveBook(@RequestBody ReserveRequest request) {
        log.info("API: Reserve book {} for user {}", request.getBookId(), request.getUserId());
        Reservation res = reservationService.reserveBook(request.getUserId(), request.getBookId());
        return ReservationMapper.toDTO(res);
    }

    @GetMapping("/user/{userId}")
    public List<ReservationDTO> getReservationsByUser(@PathVariable Long userId) {
        log.info("API: Get reservations for user {}", userId);
        List<Reservation> resList = reservationService.getReservationsByUser(userId);
        return resList.stream().map(ReservationMapper::toDTO).collect(Collectors.toList());
    }

    @PostMapping("/{id}/cancel")
    public String cancelReservation(@PathVariable Long id) {
        log.info("API: Cancel reservation {}", id);
        reservationService.cancelReservation(id);
        return "Reservation cancelled";
    }
}
