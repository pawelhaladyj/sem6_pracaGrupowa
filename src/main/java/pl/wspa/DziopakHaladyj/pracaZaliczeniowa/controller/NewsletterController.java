package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.controller;

import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.NewsletterSubscription;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.mapper.NewsletterSubscriptionMapper;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.service.NewsletterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/newsletter")
@RequiredArgsConstructor
public class NewsletterController {
    private final NewsletterService newsletterService;

    @PostMapping("/subscribe")
    public NewsletterSubscriptionDTO subscribe(@RequestBody SubscribeRequest request) {
        log.info("API: Subscribe email {}", request.getEmail());
        NewsletterSubscription sub = newsletterService.subscribe(request.getEmail());
        return NewsletterSubscriptionMapper.toDTO(sub);
    }

    @DeleteMapping("/unsubscribe/{id}")
    public String unsubscribe(@PathVariable Long id) {
        log.info("API: Unsubscribe id {}", id);
        newsletterService.unsubscribe(id);
        return "Unsubscribed successfully";
    }

    @GetMapping("/subscribers")
    public List<NewsletterSubscriptionDTO> listSubscribers() {
        log.info("API: List subscribers");
        List<NewsletterSubscription> subs = newsletterService.listSubscribers();
        return subs.stream().map(NewsletterSubscriptionMapper::toDTO).collect(Collectors.toList());
    }
}