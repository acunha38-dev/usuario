package com.javanauta.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

// Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "endereco")

public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "rua") // não definiu o tamanho!!
    private String rua;
    @Column(name = "numero")
    private Long numero;
    @Column(name = "complemento", length = 20)
    private String complemento;
    @Column(name = "cidade", length = 150)
    private String cidade;
    @Column(name = "estado", length = 2)
    private String estado;
    @Column(name = "cep", length = 9)
    private String cep;


}
