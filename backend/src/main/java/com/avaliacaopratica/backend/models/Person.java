package com.avaliacaopratica.backend.models;

import com.avaliacaopratica.backend.enums.IntegrationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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

    @Column
    private LocalDate birth;

    @Column(unique = true, length = 11)
    private String cpf;

    @Column(length = 120)
    private String email;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(name = "integration_status", nullable = false, length = 12)
    private IntegrationStatus integrationStatus = IntegrationStatus.NAO_ENVIADO;

    public void setAddress(Address address) {
        address.setPerson(this);
        this.address = address;
    }
}