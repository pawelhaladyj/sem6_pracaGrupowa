package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.service;

import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.exception.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User register(RegisterRequest request) {
        log.info("Registering new user with email: {}", request.getEmail());
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email is already in use");
        }
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CLIENT);
        User saved = userRepository.save(user);
        log.info("User registered successfully: {}", saved.getEmail());
        return saved;
    }

    public User login(LoginRequest request) {
        log.info("Attempting login for email: {}", request.getEmail());
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResourceNotFoundException("Invalid email or password");
        }
        log.info("Login successful for user: {}", user.getEmail());
        return user;
    }

    public void requestPasswordReset(PasswordResetRequest request) {
        log.info("Password reset requested for email: {}", request.getEmail());
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("No account with that email"));
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpiry(LocalDateTime.now().plusDays(1));
        userRepository.save(user);
        // In a real application, we would send this token via email to the user
        log.info("Generated password reset token for {}: {}", user.getEmail(), token);
    }

    public void confirmPasswordReset(PasswordResetConfirmRequest request) {
        log.info("Confirming password reset with token: {}", request.getToken());
        User user = userRepository.findByResetToken(request.getToken())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid or expired password reset token"));
        if (user.getResetTokenExpiry() != null && LocalDateTime.now().isAfter(user.getResetTokenExpiry())) {
            throw new ResourceNotFoundException("Reset token has expired");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
        log.info("Password reset successful for user: {}", user.getEmail());
    }
}