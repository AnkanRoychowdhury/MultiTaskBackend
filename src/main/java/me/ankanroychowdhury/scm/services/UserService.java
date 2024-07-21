package me.ankanroychowdhury.scm.services;

import me.ankanroychowdhury.scm.dtos.SignInDTO;
import me.ankanroychowdhury.scm.entities.User;

import java.util.List;

public interface UserService {
    User signUpUser(User user) throws Exception;
    List<User> getUsers() throws Exception;
    User signInUser(SignInDTO signInDTO) throws Exception;
}
