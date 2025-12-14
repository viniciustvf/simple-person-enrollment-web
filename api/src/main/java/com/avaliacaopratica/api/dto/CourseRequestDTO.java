package com.avaliacaopratica.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseRequestDTO {

    @NotBlank
    private String nome;

    @NotNull
    private Integer numeroVagas;

}
