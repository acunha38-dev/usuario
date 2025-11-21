package com.javanauta.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
// TRANSFORMAR ESSA CLASSE NUMA CLASSE ENTITY

// CLASSE ENCAPSULADA USANDO ESSAS ANOTAÇÕES(*)
// (*) - É um recurso da linguagem (introduzido no Java 5)
// que serve para adicionar metadados ao código.

// Entity


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "usuario")
// Herda do UserDetails que é gerenciador de acessos
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // é aqui o argumento do repository

    @Column(name = "nome", length = 100)
    private String nome;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "senha")
    private String senha;

    // criando FK em cascata - para quando deletar linha mãe - deletar todas as filhas
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private List<Endereco> enderecos;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private List<Telefone> telefones;


}



