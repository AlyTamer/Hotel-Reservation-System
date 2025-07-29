package com.aly.brightskies.task3.security;

import com.aly.brightskies.task3.exceptions.ConflictException;
import com.aly.brightskies.task3.exceptions.ForbiddenException;
import io.jsonwebtoken.Claims;                                    // ← new
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;  // ← new
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;                                          // ← new

@Component
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtility JWTUtility;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            if (JWTUtility.validateToken(token)) {
                username = JWTUtility.getUsername(token);
            }
        }
        else throw new ConflictException("Invalid Request");
        if (username != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            Claims claims = io.jsonwebtoken.Jwts.parserBuilder()
                    .setSigningKey(JWTUtility.getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String role = claims.get("role", String.class);
            List<SimpleGrantedAuthority> authorities =
                    List.of(new SimpleGrantedAuthority(role));
            UserDetails user = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(user, null, authorities);
            auth.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            System.out.println("JWT Username: " + username);
            System.out.println("JWT Role: " + role);
            System.out.println("Authorities: " + authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        else{
            throw new ForbiddenException("Invalid Token/Credentials");
        }

        filterChain.doFilter(request, response);
    }


}
