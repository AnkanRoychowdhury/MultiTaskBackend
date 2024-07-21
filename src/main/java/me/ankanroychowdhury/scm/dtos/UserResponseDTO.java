package me.ankanroychowdhury.scm.dtos;

import lombok.*;
import me.ankanroychowdhury.scm.entities.User;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO{
    private String email;
    private String username;
    private String password;
    private String bio;
    public static UserResponseDTO from(User user) {
        return UserResponseDTO.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .bio(user.getBio())
                .build();
    }
}
