package me.ankanroychowdhury.scm.dtos;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private String email;
    private String username;
    private String password;
    private String bio;
}
