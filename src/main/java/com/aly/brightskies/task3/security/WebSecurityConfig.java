package com.aly.brightskies.task3.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig {
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private JWTFilter jwtFilter;
    @Bean
    public AuthenticationManager authManager() throws Exception {
        return authentication -> {
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
        if(userDetails==null) throw new UsernameNotFoundException("User not found");
        //Check password
            if(!authentication.getCredentials().equals(userDetails.getPassword())){
                throw new RuntimeException("Password Invalid");
            }
        return new UsernamePasswordAuthenticationToken(userDetails,null, Collections.emptyList());}


    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeHTTPRequests -> authorizeHTTPRequests
                .requestMatchers("/login") .permitAll()
                .requestMatchers("/signin").permitAll()
                .requestMatchers("/signup").permitAll()
                .anyRequest().authenticated())
                .addFilter(jwtFilter);
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
