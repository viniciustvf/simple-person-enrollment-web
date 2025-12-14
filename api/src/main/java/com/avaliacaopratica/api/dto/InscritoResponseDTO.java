package com.avaliacaopratica.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InscritoResponseDTO {

    private Integer idRegistration;

    private String nomePessoa;

    private String nomeCurso;

    private Double nota;
}
