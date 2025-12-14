package com.avaliacaopratica.api.models;

import com.avaliacaopratica.api.enums.CourseRegistrationStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCourse;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false)
    private Integer numVacancies;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CourseRegistrationStatus courseRegistrationStatus = CourseRegistrationStatus.EM_ANDAMENTO;

    public boolean isEmAndamento() {
        return CourseRegistrationStatus.EM_ANDAMENTO.equals(this.courseRegistrationStatus);
    }

}
