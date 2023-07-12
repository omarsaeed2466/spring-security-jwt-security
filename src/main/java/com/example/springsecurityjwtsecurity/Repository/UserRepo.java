package com.example.springsecurityjwtsecurity.Repository;

import com.example.springsecurityjwtsecurity.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


import java.util.Optional;

@EnableJpaRepositories
public interface UserRepo  extends JpaRepository<User,Long> {
   public Optional<User>findByEmail(String email);
}
