package com.psicotea.backend;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Indica que esta classe contém configurações do Spring


public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(org.springframework.web.servlet.config.annotation.CorsRegistry registry) {
        registry.addMapping("/**") //Permite CORS para todos os endpoints
                .allowedOrigins("http://localhost:4200") // Permite requisições APENAS do Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP permitidos
                .allowedHeaders("*") // Permite todos os cabeçaçhos
                .allowCredentials(true); // Permite o envio de cookies e cabeçalhos de autenticação

    }
}
