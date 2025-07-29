package com.aly.brightskies.task3.security;

import com.aly.brightskies.task3.entities.User;
import com.aly.brightskies.task3.exceptions.UnauthorizedException;
import com.aly.brightskies.task3.repositories.UserRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTUtility {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expirationTime;
    private Key key;
    @Autowired
    private UserRepo userRepo;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Key getKey() {
        return key;
    }

    public String generateToken(String username) {
        User user = userRepo.findByUserName(username);
        return Jwts.builder()
                .setSubject(username)
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {

        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        if (Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .after(new Date()))
            return true;
        else {
            throw new UnauthorizedException("Invalid token, Expired and/or invalid credentials");
        }
    }


}
