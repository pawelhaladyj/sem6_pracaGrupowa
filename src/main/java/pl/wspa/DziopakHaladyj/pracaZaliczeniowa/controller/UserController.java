package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.controller;

import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.User;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.mapper.UserMapper;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(
            @RequestHeader("authToken") String authToken) {
        log.info("API: Get all users (authToken={})", authToken);
        List<User> users = userService.getAllUsers();
        List<UserDTO> dtos = users.stream().map(UserMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @RequestHeader("authToken") String authToken,
            @PathVariable Long id) {
        log.info("API: Get user by id (authToken={}, id={})", authToken, id);
        User user = userService.getUserById(id);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(
            @RequestHeader("authToken") String authToken,
            @RequestBody CreateUserRequest request) {
        log.info("API: Create new user (authToken={})", authToken);
        User user = userService.createUser(request);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @RequestHeader("authToken") String authToken,
            @PathVariable Long id,
            @RequestBody UpdateUserRequest request) {
        log.info("API: Update user (authToken={}, id={})", authToken, id);
        User user = userService.updateUser(id, request);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @RequestHeader("authToken") String authToken,
            @PathVariable Long id) {
        log.info("API: Delete user (authToken={}, id={})", authToken, id);
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
