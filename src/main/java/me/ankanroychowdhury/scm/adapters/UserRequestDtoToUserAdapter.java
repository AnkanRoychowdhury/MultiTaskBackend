package me.ankanroychowdhury.scm.adapters;

import me.ankanroychowdhury.scm.dtos.UserRequestDTO;
import me.ankanroychowdhury.scm.entities.User;

public interface UserRequestDtoToUserAdapter {
    User convertDTO(UserRequestDTO userRequestDTO);
}
