package com.avaliacaopratica.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id_person", nullable = false, unique = true)
    private Person person;

    @Column(name = "cep", nullable = false, length = 8)
    private String cep;

    @Column(name = "rua", length = 120)
    private String rua;

    @Column(name = "numero")
    private String numero;

    @Column(name = "cidade", length = 60)
    private String cidade;

    @Column(name = "uf", length = 2)
    private String uf;
}