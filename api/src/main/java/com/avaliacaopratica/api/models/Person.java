package com.avaliacaopratica.api.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPerson;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(unique = true, length = 11)
    private String cpf;

    @Column(length = 120)
    private String email;

}