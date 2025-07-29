    package com.aly.brightskies.task3.security;

    import com.aly.brightskies.task3.exceptions.UnauthorizedException;
    import com.aly.brightskies.task3.exceptions.UnauthorizedMessages;
    import com.aly.brightskies.task3.exceptions.UserException;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UsernameNotFoundException;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

    @Configuration
    @EnableWebSecurity
    @EnableMethodSecurity

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
                UserDetails userDetails;
                try {
                    userDetails = userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
                } catch (Exception e) {
                    throw new UnauthorizedException(UnauthorizedMessages.INVALID_CREDENTIALS);
                }
                if (userDetails == null) throw new UsernameNotFoundException("User not found");
                if (!passwordEncoder.matches(authentication.getCredentials().toString(),
                        userDetails.getPassword())) {
                    throw new UnauthorizedException(UnauthorizedMessages.INVALID_CREDENTIALS);
                }
                return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
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
                                    "/v3/api-docs",
                                    "/swagger-resources/**",
                                    "/swagger-ui/index.html",
                                    "/swagger-ui/index.html#/**"
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
