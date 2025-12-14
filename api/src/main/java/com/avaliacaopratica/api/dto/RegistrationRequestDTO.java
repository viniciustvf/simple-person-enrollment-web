package com.avaliacaopratica.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegistrationRequestDTO {

    @NotNull
    private Integer idCourse;

    @NotBlank
    private String cpf;

    @NotNull
    private Double nota;

}

