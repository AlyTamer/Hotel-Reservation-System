    package com.aly.brightskies.task3.security;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.BadCredentialsException;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

    import java.util.Collections;

    @Configuration
    @EnableWebSecurity

    public class WebSecurityConfig {
        private final CustomUserDetailsService userDetailsService;
        private final JWTFilter jwtFilter;


        @Autowired
        public WebSecurityConfig(CustomUserDetailsService userDetailsService, JWTFilter jwtFilter) {
            this.userDetailsService = userDetailsService;
            this.jwtFilter = jwtFilter;

        }

        @Bean
        public AuthenticationManager authManager(PasswordEncoder passwordEncoder) throws RuntimeException {
            return authentication -> {
                UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
                if (userDetails == null) throw new UsernameNotFoundException("User not found");
                if (!passwordEncoder.matches(authentication.getCredentials().toString(),
                        userDetails.getPassword())) {
                    throw new BadCredentialsException("Invalid password");
                }
                return new UsernamePasswordAuthenticationToken(userDetails, null, Collections.emptyList());
            };


        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(authorizeHTTPRequests -> authorizeHTTPRequests
                            .requestMatchers("/signin",
                                    "/signup").permitAll()
                            .requestMatchers(
                                    "/swagger-ui.html",
                                    "/swagger-ui/**",
                                    "/webjars/**",
                                    "/v3/api-docs/**",
                                    "/v3/api-docs"
                            ).permitAll()
                            .requestMatchers("/admin/**").hasRole("ADMIN")
                            .anyRequest().authenticated())
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }
