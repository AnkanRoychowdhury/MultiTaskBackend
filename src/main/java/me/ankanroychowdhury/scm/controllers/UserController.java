package me.ankanroychowdhury.scm.controllers;

import me.ankanroychowdhury.scm.adapters.UserRequestDtoToUserAdapter;
import me.ankanroychowdhury.scm.dtos.UserRequestDTO;
import me.ankanroychowdhury.scm.dtos.UserResponseDTO;
import me.ankanroychowdhury.scm.entities.User;
import me.ankanroychowdhury.scm.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    private final UserService userService;
    private final UserRequestDtoToUserAdapter userRequestDtoToUserAdapter;

    public UserController(UserService userService, UserRequestDtoToUserAdapter userRequestDtoToUserAdapter) {
        this.userService = userService;
        this.userRequestDtoToUserAdapter = userRequestDtoToUserAdapter;
    }

    @PostMapping("/users/signup")
    public ResponseEntity<?> signUpUser(@RequestBody UserRequestDTO userRequestDTO) {
        try {
            User incomingUser = this.userRequestDtoToUserAdapter.convertDTO(userRequestDTO);
            User newUser = this.userService.signUpUser(incomingUser);
            UserResponseDTO response = UserResponseDTO.from(newUser);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        try {
            List<User> users = this.userService.getUsers();
            List<UserResponseDTO> response = new ArrayList<>();
            for (User user : users) {
                response.add(UserResponseDTO.from(user));
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
