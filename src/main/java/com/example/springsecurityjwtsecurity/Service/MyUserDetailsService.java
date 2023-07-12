package com.example.springsecurityjwtsecurity.Service;

import com.example.springsecurityjwtsecurity.Repository.UserRepo;
import com.example.springsecurityjwtsecurity.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Component
@Service
public class MyUserDetailsService implements UserDetailsService {

@Autowired
private UserRepo userRepo ;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userRes = userRepo.findByEmail(username);
        if (userRes.isPresent())
            throw new UsernameNotFoundException("Could not findUser with email = " + username);
        User user = userRes.get();
        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
