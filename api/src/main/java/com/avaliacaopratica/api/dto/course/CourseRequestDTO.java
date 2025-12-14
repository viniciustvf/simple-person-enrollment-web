package com.avaliacaopratica.api.dto.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseRequestDTO {

    @NotBlank
    private String name;

    @NotNull
    private Integer numVacancies;

}
