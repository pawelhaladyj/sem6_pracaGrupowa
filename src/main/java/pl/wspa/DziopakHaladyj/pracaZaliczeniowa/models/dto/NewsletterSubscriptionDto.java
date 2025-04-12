package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.models.dto;

import lombok.Data;

import java.util.Objects;

public class NewsletterSubscriptionDto {
    private Long id;
    private String email;

    public NewsletterSubscriptionDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public NewsletterSubscriptionDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsletterSubscriptionDto that = (NewsletterSubscriptionDto) o;
        return Objects.equals(id, that.id) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
