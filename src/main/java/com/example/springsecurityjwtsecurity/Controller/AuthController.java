package com.example.springsecurityjwtsecurity.Controller;

import com.example.springsecurityjwtsecurity.Repository.UserRepo;
import com.example.springsecurityjwtsecurity.Security.JWTUtil;
import com.example.springsecurityjwtsecurity.entity.User;
import com.example.springsecurityjwtsecurity.models.LoginCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")

public class AuthController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
public Map<String,Object>registerHandler(@RequestBody User user){
        // Encoding Password using Bcrypt
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
        user = userRepo.save(user);
        String token = jwtUtil.generateToken(user.getEmail());
        return Collections.singletonMap("jwt-token",token);

    }
    @PostMapping("/login")
    public Map<String,Object>loginHandler(@RequestBody LoginCredentials body){
        try {


            UsernamePasswordAuthenticationToken authInputToken = new
                    UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());
            authManager.authenticate(authInputToken);

            String token = jwtUtil.generateToken(body.getEmail());
            return Collections.singletonMap("jwt-token", token);
        }catch (AuthenticationException authExc){
            throw new RuntimeException("Invalid Login Credentials");
        }
    }

}
