package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.repository;

import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByResetToken(String resetToken);
}