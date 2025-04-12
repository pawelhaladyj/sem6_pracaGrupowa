package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.models.dto.LoginDto;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.models.dto.PasswordResetDto;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.models.dto.UserDto;

@RestController
@RequestMapping("/api/users")
public class UserController {

    // UC-1: Register New User
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        // Tutaj logika rejestracji nowego użytkownika
        return ResponseEntity.ok("User registered successfully.");
    }

    // UC-2: User Login
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginDto loginDto) {
        // Tutaj logika autoryzacji użytkownika i generacji tokena/sesji
        return ResponseEntity.ok("User logged in successfully.");
    }

    // UC-3: Reset Password
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetDto passwordResetDto) {
        // Tutaj logika resetowania hasła użytkownika
        return ResponseEntity.ok("Password reset successfully.");
    }

    // UC-14: Manage Users - Update User Details
    @PutMapping("/manage/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        // Tutaj logika aktualizacji danych użytkownika
        return ResponseEntity.ok("User updated successfully.");
    }

    // UC-14: Manage Users - Delete User Account
    @DeleteMapping("/manage/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        // Tutaj logika usuwania konta użytkownika
        return ResponseEntity.ok("User deleted successfully.");
    }

    // UC-28: Verify User Activity
    @GetMapping("/{userId}/activity")
    public ResponseEntity<String> verifyUserActivity(@PathVariable Long userId) {
        // Tutaj logika weryfikacji aktywności użytkownika (np. logi logowań, wypożyczenia itd.)
        return ResponseEntity.ok("User activity verified.");
    }

    // UC-29: User Logout
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        // Tutaj logika wylogowania użytkownika (np. unieważnienie tokena lub sesji)
        return ResponseEntity.ok("User logged out successfully.");
    }

    // UC-30: Create Admin Account
    @PostMapping("/admin/create")
    public ResponseEntity<String> createAdminAccount(@RequestBody UserDto userDto) {
        // Tutaj logika tworzenia konta administratora
        return ResponseEntity.ok("Admin account created successfully.");
    }
}

