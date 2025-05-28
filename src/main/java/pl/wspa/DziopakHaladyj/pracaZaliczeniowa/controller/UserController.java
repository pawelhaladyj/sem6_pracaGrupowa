package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.controller;

import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.User;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.mapper.UserMapper;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public List<UserDTO> getAllUsers() {
        log.info("API: Get all users");
        List<User> users = userService.getAllUsers();
        return users.stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        log.info("API: Get user by id {}", id);
        User user = userService.getUserById(id);
        return UserMapper.toDTO(user);
    }

    @PostMapping
    public UserDTO createUser(@RequestBody CreateUserRequest request) {
        log.info("API: Create new user (admin)");
        User user = userService.createUser(request);
        return UserMapper.toDTO(user);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        log.info("API: Update user {}", id);
        User user = userService.updateUser(id, request);
        return UserMapper.toDTO(user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        log.info("API: Delete user {}", id);
        userService.deleteUser(id);
        return "User deleted successfully";
    }
}
