package com.avaliacaopratica.api.models;

import com.avaliacaopratica.api.enums.CourseRegistrationStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_course")
    private Integer idCourse;

    @Column(name = "nome", nullable = false, length = 120)
    private String nome;

    @Column(name = "numero_vagas", nullable = false)
    private Integer numeroVagas;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao_curso", nullable = false, length = 20)
    private CourseRegistrationStatus situacaoCurso = CourseRegistrationStatus.EM_ANDAMENTO;

}
