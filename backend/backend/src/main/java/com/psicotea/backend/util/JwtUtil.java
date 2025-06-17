package com.psicotea.backend.util; // Ajuste o pacote conforme sua estrutura

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException; // Importe para capturar exceção de assinatura
import io.jsonwebtoken.ExpiredJwtException; // Importe para capturar exceção de token expirado
import io.jsonwebtoken.MalformedJwtException; // Importe para capturar exceção de token malformado
import io.jsonwebtoken.UnsupportedJwtException; // Importe para capturar exceção de token não suportado


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expirationMs}")
    private long expirationMs;

    // Método para obter a chave de assinatura (SecretKey)
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Gera um JWT para um dado assunto (subject, que será o email do usuário)
    public String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject) // O assunto do token (o email do usuário)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Data de emissão
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs)) // Data de expiração
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Assina o token com a chave e algoritmo HS256
                .compact(); // Constrói o JWT em uma string compacta
    }

    // Valida o token JWT
    public boolean validateToken(String token) {
        try {

            Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            System.err.println("Assinatura JWT inválida: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.err.println("Token JWT malformado: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println("Token JWT expirado: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("Token JWT não suportado: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Argumento JWT inválido: " + e.getMessage());
        }
        return false;
    }

    // Extrai o assunto (subject, email do usuário) do token JWT
    public String getSubjectFromToken(String token) {
        // AQUI TAMBÉM ESTÁ A MUDANÇA PARA O PARSING
        return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().getSubject();
    }
}