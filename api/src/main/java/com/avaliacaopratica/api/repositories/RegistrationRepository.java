package com.avaliacaopratica.api.repositories;

import com.avaliacaopratica.api.models.Registration;
import com.avaliacaopratica.api.dto.InscritoResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, UUID> {

    long countByCourse_IdCourse(Integer idCourse);

    List<Registration> findByCourse_IdCourseOrderByNotaDesc(Integer idCourse);

    @Query("""
        SELECT new com.avaliacaopratica.api.models.dto.InscritoResponseDTO(
            r.idRegistration,
            p.name,
            c.nome,
            r.nota
        )
        FROM Registration r
        JOIN Course c ON c.idCourse = r.course.idCourse
        JOIN Person p ON p.cpf = r.cpf
        WHERE c.idCourse = :idCurso
        ORDER BY r.nota DESC
    """)
    List<InscritoResponseDTO> findInscritosByCurso(@Param("idCurso") Integer idCurso);

    @Query("""
    SELECT new com.avaliacaopratica.api.models.dto.InscritoResponseDTO(
        r.idRegistration,
        p.name,
        c.nome,
        r.nota
    )
    FROM Registration r
    JOIN r.course c
    JOIN Person p ON p.cpf = r.cpf
    WHERE c.idCourse = :idCurso
      AND r.situacaoInscricao = com.avaliacaopratica.api.enums.RegistrationStatus.SELECIONADO
    ORDER BY r.nota DESC
""")
    List<InscritoResponseDTO> findInscritosFinalizadosByCurso(@Param("idCurso") Integer idCurso);

}
