package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NewsletterSubscriptionDTO {
    private Long id;
    private String email;
    private LocalDateTime subscribeDate;
}
