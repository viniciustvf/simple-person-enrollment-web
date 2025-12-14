package com.avaliacaopratica.backend.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAddress;

    @OneToOne
    @JoinColumn(name = "id_person", nullable = false, unique = true)
    private Person person;

    @Column(nullable = false, length = 8)
    private String cep;

    @Column(length = 120)
    private String rua;

    @Column
    private String numero;

    @Column(length = 60)
    private String cidade;

    @Column(length = 2)
    private String uf;
}