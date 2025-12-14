package com.avaliacaopratica.api.models;

import com.avaliacaopratica.api.enums.RegistrationStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "registration")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registration")
    private Integer idRegistration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_course", nullable = false)
    private Course course;

    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;

    @Column(name = "nota", nullable = false)
    private Double nota;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao_inscricao", nullable = false, length = 20)
    private RegistrationStatus situacaoInscricao = RegistrationStatus.NAO_SELECIONADO;

}
