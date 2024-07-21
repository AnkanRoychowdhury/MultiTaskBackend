package me.ankanroychowdhury.scm.services;

import me.ankanroychowdhury.scm.entities.User;
import me.ankanroychowdhury.scm.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findByEmail(username);
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return user.get();
    }
}
