package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.controller;

import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.Reservation;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.mapper.ReservationMapper;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationDTO> reserveBook(
            @RequestHeader("authToken") String authToken,
            @RequestBody ReserveRequest request) {
        log.info("API: Reserve book (authToken={}, bookId={}, userId={})", authToken, request.getBookId(), request.getUserId());
        Reservation res = reservationService.reserveBook(request.getUserId(), request.getBookId());
        return ResponseEntity.ok(ReservationMapper.toDTO(res));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByUser(
            @RequestHeader("authToken") String authToken,
            @PathVariable Long userId) {
        log.info("API: Get reservations for user (authToken={}, userId={})", authToken, userId);
        List<Reservation> resList = reservationService.getReservationsByUser(userId);
        List<ReservationDTO> dtos = resList.stream()
                .map(ReservationMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<String> cancelReservation(
            @RequestHeader("authToken") String authToken,
            @PathVariable Long id) {
        log.info("API: Cancel reservation (authToken={}, reservationId={})", authToken, id);
        reservationService.cancelReservation(id);
        return ResponseEntity.ok("Reservation cancelled");
    }
}
