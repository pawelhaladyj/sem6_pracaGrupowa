package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.service;


import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.exception.*;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        log.info("Fetching user by id: {}", userId);
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User createUser(CreateUserRequest request) {
        log.info("Admin creating user with email: {}", request.getEmail());
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email is already in use");
        }
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        try {
            user.setRole(request.getRole() != null ? Role.valueOf(request.getRole()) : Role.CLIENT);
        } catch (IllegalArgumentException e) {
            user.setRole(Role.CLIENT);
        }
        User saved = userRepository.save(user);
        log.info("User created: {}", saved.getEmail());
        return saved;
    }

    public User updateUser(Long userId, UpdateUserRequest request) {
        log.info("Updating user id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new DuplicateResourceException("Email is already in use");
            }
            user.setEmail(request.getEmail());
        }
        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if (request.getRole() != null) {
            try {
                user.setRole(Role.valueOf(request.getRole()));
            } catch (IllegalArgumentException e) {
                // ignore invalid role value
            }
        }
        User updated = userRepository.save(user);
        log.info("User updated: {}", updated.getEmail());
        return updated;
    }

    public void deleteUser(Long userId) {
        log.info("Deleting user id: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        // Check for active loans
        if (loanRepository.existsByUserAndReturnDateIsNull(user)) {
            throw new OperationNotAllowedException("User has active loans and cannot be deleted");
        }
        // Also check any loan history if we want to preserve history
        if (loanRepository.existsByUser(user)) {
            throw new OperationNotAllowedException("User has loan history and cannot be deleted");
        }
        userRepository.delete(user);
        log.info("User deleted: {}", user.getEmail());
    }
}