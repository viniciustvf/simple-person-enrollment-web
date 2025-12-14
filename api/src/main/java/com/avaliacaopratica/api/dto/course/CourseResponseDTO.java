package com.avaliacaopratica.api.dto.course;

import com.avaliacaopratica.api.enums.CourseRegistrationStatus;
import lombok.Data;

@Data
public class CourseResponseDTO {

    private Integer idCourse;

    private String name;

    private Integer numVacancies;

    private CourseRegistrationStatus courseRegistrationStatus;

}

