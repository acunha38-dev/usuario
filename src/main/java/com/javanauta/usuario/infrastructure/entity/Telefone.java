package com.javanauta.usuario.infrastructure.entity;

import jakarta.persistence.*;

// Entity
@Entity
@Table(name = "telefone")

public class Telefone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "numero", length = 10)
    private String numero;
    @Column(name = "ddd", length = 3)
    private String ddd;

    public Telefone(long id, String numero, String ddd) {
        this.id = id;
        this.numero = numero;
        this.ddd = ddd;
    }

    public Telefone() {
    }

    public long getId() {
        return this.id;
    }

    public String getNumero() {
        return this.numero;
    }

    public String getDdd() {
        return this.ddd;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }
}

