package me.ankanroychowdhury.scm.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "SignUp User", description = "Create User", tags = { "Users" })
    @ApiResponses(value = {
            @ApiResponse (
                    responseCode = "201",
                    description = "Successful user creation",
                    content = @Content(schema = @Schema(implementation = User.class))
            )
    })
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

    @Operation(summary = "SignIn User", description = "SignIn User", tags = {"Users"})
    @Parameter(in = ParameterIn.HEADER, description = "Bearer Token", content = @Content(schema = @Schema(type = "string")))
    @ApiResponses(value = {
            @ApiResponse (
                    responseCode = "200",
                    description = "Successful user SignIn",
                    content = @Content(schema = @Schema(implementation = AuthResponseDTO.class))
            )
    })
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

    @Operation(summary = "Get Users", description = "Get all user details", tags = { "Users" })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully fetched users",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class)))
            )
    })
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
