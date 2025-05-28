package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto;

import lombok.Data;

@Data
public class PasswordResetConfirmRequest {
    private String token;
    private String newPassword;
}
