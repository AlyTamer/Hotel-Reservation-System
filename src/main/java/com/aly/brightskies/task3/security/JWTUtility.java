package com.aly.brightskies.task3.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTUtility {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expirationTime}")
    private int expirationTime;
    private Key key;
    @PostConstruct
    public void init() {
        this.key= Keys.hmacShaKeyFor(secret.getBytes());
    }
    public String generateToken(String username){
        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+expirationTime))
                .compact();
    }
    public String getUsername(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            //TODO IMPLEMENT EXPIRY CHECK
            return true;
        }
        catch (Exception e){
            System.out.println("Failed to validate token: " + e.getMessage());
            return false;
        }
    }

}
