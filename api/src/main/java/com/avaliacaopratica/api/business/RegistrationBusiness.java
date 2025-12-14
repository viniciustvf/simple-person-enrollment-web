package com.avaliacaopratica.api.business;

import com.avaliacaopratica.api.exceptions.BusinessException;
import com.avaliacaopratica.api.enums.CourseRegistrationStatus;
import com.avaliacaopratica.api.enums.RegistrationStatus;
import com.avaliacaopratica.api.mapper.RegistrationMapper;
import com.avaliacaopratica.api.messaging.producer.RegistrationProducer;
import com.avaliacaopratica.api.models.Course;
import com.avaliacaopratica.api.models.Registration;
import com.avaliacaopratica.api.dto.InscritoResponseDTO;
import com.avaliacaopratica.api.dto.RegistrationRequestDTO;
import com.avaliacaopratica.api.dto.RegistrationResponseDTO;
import com.avaliacaopratica.api.repositories.CourseRepository;
import com.avaliacaopratica.api.repositories.RegistrationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RegistrationBusiness {

    private final RegistrationRepository registrationRepository;
    private final RegistrationMapper registrationMapper;
    private final CourseRepository courseRepository;
    private final RegistrationProducer registrationProducer;

    public RegistrationResponseDTO create(RegistrationRequestDTO request) {
        Course course = courseRepository.findById(request.getIdCourse()).orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));

        validate(course);

        Registration registration = registrationMapper.toEntity(request);
        registration.setCourse(course);

        registrationRepository.save(registration);

        return registrationMapper.toResponse(registration);
    }

    public List<InscritoResponseDTO> findInscritosByCurso(Integer idCurso) {
        return registrationRepository.findInscritosByCurso(idCurso);
    }

    public void enfileirarFinalizacao(Integer idCurso) {
        Course course = courseRepository.findById(idCurso).orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));

        if (course.getSituacaoCurso() != CourseRegistrationStatus.EM_ANDAMENTO) {
            throw new BusinessException("Curso não está em andamento");
        }

        registrationProducer.enviarParaFila(idCurso);
    }

    @Transactional
    public void finalizarInscricoesCurso(Integer idCurso) {
        log.info("Iniciando finalização das inscrições do curso {}", idCurso);

        Course course = courseRepository.findById(idCurso).orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));

        List<Registration> registrations = registrationRepository.findByCourse_IdCourseOrderByNotaDesc(idCurso);

        int vagas = course.getNumeroVagas();

        IntStream.range(0, registrations.size())
                .forEach(index -> {
                    Registration registration = registrations.get(index);
                    registration.setSituacaoInscricao(index < vagas ? RegistrationStatus.SELECIONADO : RegistrationStatus.NAO_SELECIONADO);});

        course.setSituacaoCurso(CourseRegistrationStatus.FINALIZADA);

        registrationRepository.saveAll(registrations);
        courseRepository.save(course);

        log.info(
                "Finalização concluída para o curso {}. Vagas: {}, Inscritos: {}",
                idCurso,
                vagas,
                registrations.size()
        );
    }

    public List<InscritoResponseDTO> findInscritosFinalizadosByCurso(Integer idCurso) {
        return registrationRepository.findInscritosFinalizadosByCurso(idCurso);
    }

    private void validate(Course course) {
        if (course.getSituacaoCurso() != CourseRegistrationStatus.EM_ANDAMENTO) {
            throw new BusinessException("Curso não está em andamento");
        }
    }
}
