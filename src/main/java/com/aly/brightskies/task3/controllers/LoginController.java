package com.aly.brightskies.task3.controllers;

import com.aly.brightskies.task3.dto.UserDTO;
import com.aly.brightskies.task3.entities.Role;
import com.aly.brightskies.task3.entities.User;
import com.aly.brightskies.task3.exceptions.*;
import com.aly.brightskies.task3.repositories.UserRepo;
import com.aly.brightskies.task3.security.JWTUtility;
import com.aly.brightskies.task3.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Login", description = "User login and registration")
public class LoginController {

    private final UserRepo userRepo;
    private final AuthenticationManager authManager;
    private final JWTUtility jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Autowired
    public LoginController(UserRepo userRepo,
                           AuthenticationManager authManager,
                           JWTUtility jwtUtil,
                           PasswordEncoder passwordEncoder,
                           UserService userService) {
        this.userRepo = userRepo;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Operation(
            summary = "User Signup",
            description = "Register a new user with email and password. If the email is already in use, returns a conflict status."
    )
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDTO user) {
        if (userService.existsByUsername(user.getUsername()))
            throw new UserException(UserExceptionMessages.USERNAME_ALREADY_EXIST);
        if (userService.existsByEmail((user.getEmail())))
            throw new UserException(UserExceptionMessages.EMAIL_ALREADY_EXIST);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() != Role.ROLE_USER) {
            user.setRole(Role.ROLE_USER);
        }
        User saved = userRepo.save(
                new User(
                        user.getUsername(),
                        user.getEmail(),
                        user.getNumber(),
                        user.getPassword(),
                        user.getRole()
                )
        );
        String token = jwtUtil.generateToken(saved.getUserName());
        System.out.println("Generated token for" + user.getUsername() + " with Role " + user.getRole() + ":\n" + token);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(saved);
    }

    @Operation(
            summary = "User Signin",
            description = "Authenticate a user with username and password. Returns a JWT token if successful."
    )
    @PostMapping("/signin")
    public ResponseEntity<User> signin(@RequestBody User user) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUserName(),
                            user.getPassword()
                    )
            );
            String token = jwtUtil.generateToken(auth.getName());
            User loggedIn = userRepo.findByUserName(auth.getName());
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body(loggedIn);
        } catch (Exception ex) {
            throw new UnauthorizedException(UnauthorizedMessages.INVALID_CREDENTIALS);
        }
    }
}
