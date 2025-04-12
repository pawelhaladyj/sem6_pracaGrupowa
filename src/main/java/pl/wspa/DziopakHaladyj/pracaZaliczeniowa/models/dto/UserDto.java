package pl.wspa.DziopakHaladyj.pracaZaliczeniowa.models.dto;

import lombok.Data;

@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
