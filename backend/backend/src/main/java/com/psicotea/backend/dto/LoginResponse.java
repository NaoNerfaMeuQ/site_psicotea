package com.psicotea.backend.dto;

import lombok.AllArgsConstructor; // Construtor com todos os argumentos
import lombok.Data;
import lombok.NoArgsConstructor; // Construtor sem args

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LoginResponse {
    private String token;
    private String message;// Opcional, para mensagens como "Login bem-sucedido"

}
