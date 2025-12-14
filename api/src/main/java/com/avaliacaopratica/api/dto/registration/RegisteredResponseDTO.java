package com.avaliacaopratica.api.dto.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisteredResponseDTO {

    private Integer idRegistration;

    private String nomePessoa;

    private String nomeCurso;

    private Double nota;

}
