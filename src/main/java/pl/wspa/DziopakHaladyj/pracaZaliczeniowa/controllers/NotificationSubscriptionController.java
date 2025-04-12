package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.models.dto.NewsletterSubscriptionDto;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationSubscriptionController {

    // UC-17: Subscribe to Newsletter
    // Endpoint umożliwiający zapisanie się użytkownika do newslettera.
    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribeNewsletter(@RequestBody NewsletterSubscriptionDto subscriptionDto) {
        // Tutaj należy zaimplementować logikę zapisu subskrypcji (np. zapis do bazy danych)
        return ResponseEntity.ok("Subscription successful for email: " + subscriptionDto.getEmail());
    }

    // UC-18: Manage Subscriptions - Get list of subscriptions (dla administratora)
    // Endpoint umożliwiający pobranie listy wszystkich subskrypcji.
    @GetMapping("/subscriptions")
    public ResponseEntity<List<NewsletterSubscriptionDto>> getAllSubscriptions() {
        // W realnej aplikacji pobierz listę z bazy danych.
        // Poniżej przykładowa lista stworzona na potrzeby demonstracji.
        List<NewsletterSubscriptionDto> subscriptions = new ArrayList<>();
        subscriptions.add(new NewsletterSubscriptionDto());
        subscriptions.add(new NewsletterSubscriptionDto());
        return ResponseEntity.ok(subscriptions);
    }

    // UC-18: Manage Subscriptions - Delete a subscription (dla administratora)
    // Endpoint umożliwiający usunięcie subskrypcji po podanym ID.
    @DeleteMapping("/subscriptions/{subscriptionId}")
    public ResponseEntity<String> deleteSubscription(@PathVariable Long subscriptionId) {
        // Tutaj należy zaimplementować logikę usunięcia subskrypcji z bazy danych.
        return ResponseEntity.ok("Subscription with ID " + subscriptionId + " deleted successfully.");
    }

    // UC-22: Send Notification for New Books
    // Endpoint inicjujący wysyłkę powiadomienia o nowościach do wszystkich subskrybentów.
    @PostMapping("/notify")
    public ResponseEntity<String> notifyNewBooks() {
        // Tutaj należy dodać logikę wysyłki powiadomień (np. wysyłka e-mail do subskrybentów)
        return ResponseEntity.ok("New books notification sent to all subscribers.");
    }
}
