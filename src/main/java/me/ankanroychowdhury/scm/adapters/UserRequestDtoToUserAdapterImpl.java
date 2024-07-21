package me.ankanroychowdhury.scm.adapters;

import me.ankanroychowdhury.scm.dtos.UserRequestDTO;
import me.ankanroychowdhury.scm.entities.Role;
import me.ankanroychowdhury.scm.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserRequestDtoToUserAdapterImpl implements UserRequestDtoToUserAdapter {

    @Override
    public User convertDTO(UserRequestDTO userRequestDTO) {
        return User.builder()
        .email(userRequestDTO.getEmail())
        .username(userRequestDTO.getUsername())
        .password(userRequestDTO.getPassword())
        .bio(userRequestDTO.getBio())
        .role(Role.USER)
        .build();
    }
}
