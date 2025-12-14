package com.avaliacaopratica.backend.models;

import com.avaliacaopratica.backend.enums.IntegrationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "person")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_person")
    private Integer idPerson;

    @Column(name = "nome", nullable = false, length = 120)
    private String name;

    @Column(name = "birth")
    private LocalDate birth;

    @Column(name = "cpf", unique = true, length = 11)
    private String cpf;

    @Column(name = "email", length = 120)
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