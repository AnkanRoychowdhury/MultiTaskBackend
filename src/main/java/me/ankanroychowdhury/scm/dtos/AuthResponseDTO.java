package me.ankanroychowdhury.scm.dtos;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {
    public Boolean success;
    public String token;
}
