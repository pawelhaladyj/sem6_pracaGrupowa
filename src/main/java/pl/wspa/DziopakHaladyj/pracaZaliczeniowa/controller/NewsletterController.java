package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.controller;

import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.NewsletterSubscription;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.mapper.NewsletterSubscriptionMapper;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.service.NewsletterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/newsletter")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class NewsletterController {
    private final NewsletterService newsletterService;

    @PostMapping("/subscribe")
    public ResponseEntity<NewsletterSubscriptionDTO> subscribe(@RequestBody SubscribeRequest request) {
        log.info("API: Subscribe email {}", request.getEmail());
        NewsletterSubscription sub = newsletterService.subscribe(request.getEmail());
        return ResponseEntity.ok(NewsletterSubscriptionMapper.toDTO(sub));
    }

    @DeleteMapping("/unsubscribe/{id}")
    public ResponseEntity<String> unsubscribe(
            @RequestHeader("authToken") String authToken,
            @PathVariable Long id) {
        log.info("API: Unsubscribe (authToken={}, id={})", authToken, id);
        newsletterService.unsubscribe(id);
        return ResponseEntity.ok("Unsubscribed successfully");
    }

    @GetMapping("/subscribers")
    public ResponseEntity<List<NewsletterSubscriptionDTO>> listSubscribers(
            @RequestHeader("authToken") String authToken) {
        log.info("API: List subscribers (authToken={})", authToken);
        List<NewsletterSubscription> subs = newsletterService.listSubscribers();
        List<NewsletterSubscriptionDTO> dtos = subs.stream()
                .map(NewsletterSubscriptionMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
