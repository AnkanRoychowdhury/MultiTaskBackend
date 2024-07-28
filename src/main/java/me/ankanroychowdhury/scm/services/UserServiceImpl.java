package me.ankanroychowdhury.scm.services;

import jakarta.persistence.EntityNotFoundException;
import me.ankanroychowdhury.scm.dtos.SignInDTO;
import me.ankanroychowdhury.scm.entities.User;
import me.ankanroychowdhury.scm.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User signUpUser(User user) throws Exception{
        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            return this.userRepository.save(user);
        }catch(Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    public User signInUser(SignInDTO signInDTO) throws Exception{
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signInDTO.getEmail(),
                    signInDTO.getPassword()
            ));
            return this.userRepository.findByEmail(signInDTO.getEmail()).orElseThrow(EntityNotFoundException::new);
        }catch(Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    public List<User> getUsers() throws Exception {
        try {
            return this.userRepository.findAll();
        }catch(Exception e) {
            e.printStackTrace();
            throw new EntityNotFoundException("User could not be found");
        }
    }

    public User findUserByEmail(String email) throws Exception {
        try {
            return this.userRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        }catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

}
