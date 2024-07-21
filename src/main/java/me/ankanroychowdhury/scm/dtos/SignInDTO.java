package me.ankanroychowdhury.scm.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInDTO {
    private String email;
    private String password;
}
