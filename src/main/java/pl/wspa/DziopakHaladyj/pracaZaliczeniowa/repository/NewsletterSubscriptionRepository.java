package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.NewsletterSubscription;

import java.util.Optional;

public interface NewsletterSubscriptionRepository extends JpaRepository<NewsletterSubscription, Long> {
    Optional<NewsletterSubscription> findByEmail(String email);
    boolean existsByEmail(String email);
}
