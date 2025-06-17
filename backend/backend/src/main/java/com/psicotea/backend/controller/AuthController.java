// src/main/java/com/example.backend/controller/AuthController.java

package com.psicotea.backend.controller;

import com.psicotea.backend.dto.LoginRequest;
import com.psicotea.backend.dto.LoginResponse; // Certifique-se que esta importação está correta
import com.psicotea.backend.dto.RegisterRequest;
import com.psicotea.backend.model.User;
import com.psicotea.backend.repository.UserRepository;
import com.psicotea.backend.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            return new ResponseEntity<>("Email já está em uso!", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        userRepository.save(user);

        return new ResponseEntity<>("Usuário registrado com sucesso!", HttpStatus.CREATED);
    }

    // REVISTE ESTE MÉTODO CUIDADOSAMENTE
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElse(null);

        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return new ResponseEntity<>("Credenciais inválidas!", HttpStatus.UNAUTHORIZED);
        }

        // GERA O JWT SE O LOGIN FOR BEM-SUCEDIDO
        String token = jwtUtil.generateToken(user.getEmail());

        // MUITO IMPORTANTE: Garanta que está retornando um objeto LoginResponse
        // e que a ResponseEntity está encapsulando ESSE OBJETO.
        return new ResponseEntity<>(new LoginResponse(token, "Login bem-sucedido!"), HttpStatus.OK);
    }
}