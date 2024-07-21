package me.ankanroychowdhury.scm.controllers;

import jakarta.servlet.http.HttpServletResponse;
import me.ankanroychowdhury.scm.adapters.UserRequestDtoToUserAdapter;
import me.ankanroychowdhury.scm.dtos.AuthResponseDTO;
import me.ankanroychowdhury.scm.dtos.SignInDTO;
import me.ankanroychowdhury.scm.dtos.UserRequestDTO;
import me.ankanroychowdhury.scm.dtos.UserResponseDTO;
import me.ankanroychowdhury.scm.entities.User;
import me.ankanroychowdhury.scm.services.JwtService;
import me.ankanroychowdhury.scm.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserRequestDtoToUserAdapter userRequestDtoToUserAdapter;
    private final JwtService jwtService;

    public UserController(UserService userService, UserRequestDtoToUserAdapter userRequestDtoToUserAdapter, JwtService jwtService) {
        this.userService = userService;
        this.userRequestDtoToUserAdapter = userRequestDtoToUserAdapter;
        this.jwtService = jwtService;
    }

    @PostMapping("/auth/signup")
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

    @PostMapping("/auth/signin")
    public ResponseEntity<?> signUpUser(@RequestBody SignInDTO signInDTO, HttpServletResponse response) {
        try {
            User authenticatedUser = this.userService.signInUser(signInDTO);
            String jwtToken = this.jwtService.generateToken(authenticatedUser.getEmail());
            ResponseCookie cookie = ResponseCookie
                    .from("JwtToken", jwtToken)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(7*24*3600)
                    .build();
            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return new ResponseEntity<>(AuthResponseDTO.builder().success(true).token(jwtToken).build(), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping
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
