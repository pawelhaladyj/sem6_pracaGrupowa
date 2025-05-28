package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "subscriptions")
public class NewsletterSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subscription")
    private Long id;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "subscribe_date", nullable = false)
    private LocalDateTime subscribeDate;

    @PrePersist
    public void prePersist() {
        this.subscribeDate = LocalDateTime.now();
    }
}