package com.javanauta.usuario.infrastructure.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
// TRANSFORMAR ESSA CLASSE NUMA CLASSE ENTITY

// CLASSE ENCAPSULADA USANDO ESSAS ANOTAÇÕES(*)
// (*) - É um recurso da linguagem (introduzido no Java 5)
// que serve para adicionar metadados ao código.

// Entity
@Entity
@Table(name = "usuario")


// Herda do UserDetails que é gerenciador de acessos
public class Usuario implements UserDetails {
    /* EXEMPLO PARA O GETTER E SETTER -
    private String nome;
    private String email;
    private String senha;
    */

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

    public Usuario(long id, String nome, String email, String senha, List<Endereco> enderecos, List<Telefone> telefones) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.enderecos = enderecos;
        this.telefones = telefones;
    }

    public Usuario() {
    }

    // ROLES DE ACESSO - NÃO VAMOS USAR AGORA
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public long getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getEmail() {
        return this.email;
    }

    public String getSenha() {
        return this.senha;
    }

    public List<Endereco> getEnderecos() {
        return this.enderecos;
    }

    public List<Telefone> getTelefones() {
        return this.telefones;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }
}



