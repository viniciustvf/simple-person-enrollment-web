package com.avaliacaopratica.api.dto;

import com.avaliacaopratica.api.enums.RegistrationStatus;
import lombok.Data;

@Data
public class RegistrationResponseDTO {

    private Integer idRegistration;

    private Integer idCourse;

    private String nomeCurso;

    private String cpf;

    private Double nota;

    private RegistrationStatus situacaoInscricao;
}
