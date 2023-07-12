package com.example.springsecurityjwtsecurity.Configration;


import com.example.springsecurityjwtsecurity.Security.JWTFilter;
import com.example.springsecurityjwtsecurity.Service.MyUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter {


    @Autowired
    private JWTFilter filter;
    @Autowired
    private MyUserDetailsService uds;

    @Override
    protected void configure(HttpSecurity http) throws Exception { // Method to configure your app security
        http.csrf().disable() // Disabling csrf
                .httpBasic().disable() // Disabling http basic
                .cors() // Enabling cors
                .and()
                .authorizeHttpRequests()// Authorizing incoming requests
                .antMatchers("/api/auth/**")
                .permitAll() // Allows auth requests to be made without authentication of any sort
                .antMatchers("/api/user/**")
                .hasRole("USER") // Allows only users with the "USER" role to make requests to the user routes
                .and()
                .userDetailsService(uds) // Setting the user details service to the custom implementation
                .exceptionHandling()
                .authenticationEntryPoint(

                        (request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                )
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Setting Session to be stateless

       // addFilterBefor(new RestAuthenticationProcessingFilter(), BasicAuthenticationFilter.class)
http.addFilterBefore(filter,new UsernamePasswordAuthenticationFilter().getClass());
    }
@Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    
@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
