package com.avaliacaopratica.api.dto;

import com.avaliacaopratica.api.enums.CourseRegistrationStatus;
import lombok.Data;

@Data
public class CourseResponseDTO {

    private Integer idCourse;

    private String nome;

    private Integer numeroVagas;

    private CourseRegistrationStatus situacaoCurso;
}

