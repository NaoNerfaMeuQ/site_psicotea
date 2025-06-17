package com.psicotea.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF
                .cors(Customizer.withDefaults()) // Habilita o CORS que configuramos
                .authorizeHttpRequests(auth -> auth
                        // Permite acesso ao H2 Console
                        .requestMatchers("/h2-console/**").permitAll()
                        // Mantenha as outras linhas
                        .requestMatchers("/api/hello", "/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                // CORREÇÃO: HABILITE O FRAME OPTIONS PARA O H2 CONSOLE DESTA FORMA
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable()) // <-- LINHA CORRIGIDA
                );

        return http.build();
    }
}
