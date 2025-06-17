package com.psicotea.backend.repository;
import com.psicotea.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
// JpaRepository fornece métodos CRUD básicos (salvar, buscar, deletar, etc.)

public interface UserRepository  extends JpaRepository<User, Long> {
    // Encontra um usuário pelo email (método de busca personalizado)
    Optional<User> findByEmail(String email);
}
