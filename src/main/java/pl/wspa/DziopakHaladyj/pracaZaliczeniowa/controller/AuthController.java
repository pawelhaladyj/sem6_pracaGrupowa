package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.controller;

import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.User;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.mapper.UserMapper;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterRequest request) {
        log.info("API: Register new user");
        User user = authService.register(request);
        UserDTO dto = UserMapper.toDTO(user);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequest request) {
        log.info("API: User login");
        User user = authService.login(request);
        UserDTO userDTO = UserMapper.toDTO(user);
        userDTO.setToken(UUID.randomUUID().toString());
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> requestResetPassword(
            @RequestHeader("authToken") String authToken,
            @RequestBody PasswordResetRequest request) {
        log.info("API: Request password reset (authToken: {})", authToken);
        authService.requestPasswordReset(request);
        return ResponseEntity.ok("Password reset link sent (check logs for token)");
    }

    @PostMapping("/reset-password/confirm")
    public ResponseEntity<String> confirmResetPassword(
            @RequestHeader("authToken") String authToken,
            @RequestBody PasswordResetConfirmRequest request) {
        log.info("API: Confirm password reset (authToken: {})", authToken);
        authService.confirmPasswordReset(request);
        return ResponseEntity.ok("Password has been reset successfully");
    }
}