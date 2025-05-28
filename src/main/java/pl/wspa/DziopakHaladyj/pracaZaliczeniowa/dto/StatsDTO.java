package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto;

import lombok.Data;

@Data
public class StatsDTO {
    private long totalUsers;
    private long totalBooks;
    private long totalLoans;
    private long activeLoans;
    private long activeReservations;
    private long totalSubscribers;
    private long overdueLoans;
}