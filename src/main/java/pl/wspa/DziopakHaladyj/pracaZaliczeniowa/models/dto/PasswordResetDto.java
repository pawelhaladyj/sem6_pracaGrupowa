package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.models.dto;

import lombok.Data;

@Data
public class PasswordResetDto {
    private String email;
    private String newPassword;
}
