package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.mapper;


import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.dto.UserDTO;
import pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.User;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().name());
        return dto;
    }

    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;
        User user = new User();
        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        // Uwaga: hasło nie jest ustawiane tutaj (powinno być ustawione osobno i haszowane)
        if (dto.getRole() != null) {
            try {
                user.setRole(Enum.valueOf(pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.Role.class, dto.getRole()));
            } catch (Exception e) {
                user.setRole(pl.wspa.DziopakHaladyj.pracaZaliczeniowa.entity.Role.CLIENT);
            }
        }
        return user;
    }
}
