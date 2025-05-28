package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.mapper;

import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto.ReservationDTO;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.Reservation;

public class ReservationMapper {
    public static ReservationDTO toDTO(Reservation reservation) {
        if (reservation == null) return null;
        ReservationDTO dto = new ReservationDTO();
        dto.setId(reservation.getId());
        dto.setBookId(reservation.getBook().getId());
        dto.setBookTitle(reservation.getBook().getTitle());
        dto.setUserId(reservation.getUser().getId());
        dto.setUserName(reservation.getUser().getFirstName() + " " + reservation.getUser().getLastName());
        dto.setReservationDate(reservation.getReservationDate());
        dto.setStatus(reservation.getStatus().name());
        return dto;
    }
}