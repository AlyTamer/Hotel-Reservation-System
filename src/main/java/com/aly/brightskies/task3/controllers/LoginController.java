package com.aly.brightskies.task3.controllers;

import com.aly.brightskies.task3.entities.Role;
import com.aly.brightskies.task3.entities.User;
import com.aly.brightskies.task3.repositories.UserRepo;
import com.aly.brightskies.task3.security.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    private final UserRepo userRepo;
    private final AuthenticationManager authManager;
    private final JWTUtility jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginController(UserRepo userRepo,
                           AuthenticationManager authManager,
                           JWTUtility jwtUtil,
                           PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole(Role.ROLE_USER);
        }
        User saved = userRepo.save(user);
        String token = jwtUtil.generateToken(saved.getName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(saved);
    }

    @PostMapping("/signin")
    public ResponseEntity<User> signin(@RequestBody User user) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getName(),
                            user.getPassword()
                    )
            );
            String token = jwtUtil.generateToken(auth.getName());
            User loggedIn = userRepo.findByName(auth.getName());
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body(loggedIn);
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
