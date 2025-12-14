package com.avaliacaopratica.api.dto.registration;

import com.avaliacaopratica.api.enums.RegistrationStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationResponseDTO {

    private Integer idRegistration;

    private Integer idCourse;

    private String nomeCurso;

    private String cpf;

    private Double nota;

    private RegistrationStatus situacaoInscricao;

}
