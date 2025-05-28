package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.controller;

import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.User;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.mapper.UserMapper;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public UserDTO register(@RequestBody RegisterRequest request) {
        log.info("API: Register new user");
        User user = authService.register(request);
        return UserMapper.toDTO(user);
    }

    @PostMapping("/login")
    public UserDTO login(@RequestBody LoginRequest request) {
        log.info("API: User login");
        User user = authService.login(request);
        return UserMapper.toDTO(user);
    }

    @PostMapping("/reset-password")
    public String requestResetPassword(@RequestBody PasswordResetRequest request) {
        log.info("API: Request password reset");
        authService.requestPasswordReset(request);
        return "Password reset link sent (check logs for token)";
    }

    @PostMapping("/reset-password/confirm")
    public String confirmResetPassword(@RequestBody PasswordResetConfirmRequest request) {
        log.info("API: Confirm password reset");
        authService.confirmPasswordReset(request);
        return "Password has been reset successfully";
    }
}
