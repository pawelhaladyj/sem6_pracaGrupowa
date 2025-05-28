package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.service;

import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.exception.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NewsletterService {
    private final NewsletterSubscriptionRepository subscriptionRepository;

    public NewsletterSubscription subscribe(String email) {
        log.info("Subscribing email: {}", email);
        if (subscriptionRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("Email is already subscribed");
        }
        NewsletterSubscription sub = new NewsletterSubscription();
        sub.setEmail(email);
        NewsletterSubscription saved = subscriptionRepository.save(sub);
        log.info("Email {} subscribed (id={})", saved.getEmail(), saved.getId());
        return saved;
    }

    public void unsubscribe(Long subscriptionId) {
        log.info("Unsubscribing id: {}", subscriptionId);
        NewsletterSubscription sub = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));
        subscriptionRepository.delete(sub);
        log.info("Unsubscribed email {}", sub.getEmail());
    }

    public List<NewsletterSubscription> listSubscribers() {
        log.info("Listing all newsletter subscribers");
        return subscriptionRepository.findAll();
    }

    public void notifyNewBook(Book book) {
        log.info("Notifying subscribers of new book: {}", book.getTitle());
        List<NewsletterSubscription> subs = subscriptionRepository.findAll();
        for (NewsletterSubscription sub : subs) {
            log.info("Sending email to {} about new book '{}'", sub.getEmail(), book.getTitle());
        }
    }
}