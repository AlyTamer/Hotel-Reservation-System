package com.aly.brightskies.task3.security;

import com.aly.brightskies.task3.entities.User;                // your JPA entity
import com.aly.brightskies.task3.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User appUser = userRepo.findByName(username);
        if (appUser == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }


        return new org.springframework.security.core.userdetails.User(
                appUser.getName(),
                appUser.getPassword(),
                Collections.emptyList()
        );
    }
}
