package me.ankanroychowdhury.scm.services;

import jakarta.persistence.EntityNotFoundException;
import me.ankanroychowdhury.scm.entities.User;
import me.ankanroychowdhury.scm.repositories.UserRepository;
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

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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

    public List<User> getUsers() throws Exception {
        try {
            return this.userRepository.findAll();
        }catch(Exception e) {
            e.printStackTrace();
            throw new EntityNotFoundException("User could not be found");
        }
    }

}
