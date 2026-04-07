package com.nikhil.educonnect.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        http
                // Disable CSRF — not needed for REST APIs
                .csrf(csrf -> csrf.disable())

                // No sessions — JWT handles auth
                .sessionManagement(session -> session
                        .sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // Define which endpoints need auth
                .authorizeHttpRequests(auth -> auth

                        // Public endpoints — no token needed
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/", "/health").permitAll()

                        // GET requests — anyone can search
                        .requestMatchers(HttpMethod.GET,
                                "/api/teachers/**").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/schools/**").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/jobs/**").permitAll()

                        // Add these lines after existing GET permissions
                        .requestMatchers(HttpMethod.GET,
                                "/api/reviews/**").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/attendance/**").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/earnings/**").permitAll()

                        // New public routes
                        .requestMatchers(HttpMethod.GET,
                                "/api/notifications/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH,
                                "/api/notifications/**").permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/api/checkin/**").permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/api/checkin/**").permitAll()

                        // Teacher only
                        .requestMatchers(HttpMethod.POST,
                                "/api/jobs/*/apply")
                        .hasRole("TEACHER")

                        // School only
                        .requestMatchers(HttpMethod.POST,
                                "/api/jobs/create")
                        .hasRole("SCHOOL")
                        .requestMatchers(HttpMethod.PATCH,
                                "/api/jobs/applications/*/status")
                        .hasRole("SCHOOL")

                        // Everything else needs a valid token
                        .anyRequest().authenticated()
                )

                // Add JWT filter before Spring's default filter
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    // BCrypt password encoder — used to hash passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
