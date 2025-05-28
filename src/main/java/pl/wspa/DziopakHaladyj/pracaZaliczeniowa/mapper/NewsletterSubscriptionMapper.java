package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.mapper;


import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto.NewsletterSubscriptionDTO;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.NewsletterSubscription;

public class NewsletterSubscriptionMapper {
    public static NewsletterSubscriptionDTO toDTO(NewsletterSubscription subscription) {
        if (subscription == null) return null;
        NewsletterSubscriptionDTO dto = new NewsletterSubscriptionDTO();
        dto.setId(subscription.getId());
        dto.setEmail(subscription.getEmail());
        dto.setSubscribeDate(subscription.getSubscribeDate());
        return dto;
    }
}