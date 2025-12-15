package com.avaliacaopratica.api.repositories;

import com.avaliacaopratica.api.models.Registration;
import com.avaliacaopratica.api.dto.registration.RegisteredResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, UUID> {

    long countByCourse_IdCourse(Integer idCourse);

    List<Registration> findByCourse_IdCourseOrderByScoreDesc(Integer idCourse);

    boolean existsByCourse_IdCourseAndCpf(Integer idCourse, String cpf);

    boolean existsByCpf(String cpf);

    @Query("""
        SELECT new com.avaliacaopratica.api.dto.registration.RegisteredResponseDTO(
            r.idRegistration,
            p.name,
            c.name,
            r.score
        )
        FROM Registration r
        JOIN Course c ON c.idCourse = r.course.idCourse
        JOIN Person p ON p.cpf = r.cpf
        WHERE c.idCourse = :idCurso
        ORDER BY r.score DESC
    """)
    List<RegisteredResponseDTO> findInscritosByCurso(@Param("idCurso") Integer idCurso);

    @Query("""
    SELECT new com.avaliacaopratica.api.dto.registration.RegisteredResponseDTO(
        r.idRegistration,
        p.name,
        c.name,
        r.score
    )
    FROM Registration r
    JOIN r.course c
    JOIN Person p ON p.cpf = r.cpf
    WHERE c.idCourse = :idCurso
      AND r.registrationStatus = com.avaliacaopratica.api.enums.RegistrationStatus.SELECIONADO
    ORDER BY r.score DESC
""")
    List<RegisteredResponseDTO> findInscritosFinalizadosByCurso(@Param("idCurso") Integer idCurso);

}
