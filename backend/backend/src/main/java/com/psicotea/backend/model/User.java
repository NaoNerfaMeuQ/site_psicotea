package com.psicotea.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity // Marca a classe como uma entidade JPA (mapeada para uma tabela no DB)
@Table(name = "app_user")
@Data // Anotação do Lombok para gerar getter's, setters, toString, equals, hashCode

public class User {

    @Id //Marca o campo como chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura auto-incremento para o ID
    private Long id;

    @Column(unique = true, nullable = false) // Garante que o email seja único e não nulo
    private String email;

    @Column(nullable = false) // A senha não pode ser nula
    private String password; // IMPORTANTE: Armazenaremos a senha criptografada, não em texto plano!

    // Lombok cuida dos construtores, getters e setters via @Data



}
