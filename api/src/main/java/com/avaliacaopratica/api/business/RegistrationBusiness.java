package com.avaliacaopratica.api.business;

import com.avaliacaopratica.api.dto.registration.FinishRegistrationRequestDTO;
import com.avaliacaopratica.api.exceptions.BusinessException;
import com.avaliacaopratica.api.enums.CourseRegistrationStatus;
import com.avaliacaopratica.api.enums.RegistrationStatus;
import com.avaliacaopratica.api.mapper.RegistrationMapper;
import com.avaliacaopratica.api.messaging.producer.RegistrationProducer;
import com.avaliacaopratica.api.models.Course;
import com.avaliacaopratica.api.models.Registration;
import com.avaliacaopratica.api.dto.registration.RegisteredResponseDTO;
import com.avaliacaopratica.api.dto.registration.RegistrationRequestDTO;
import com.avaliacaopratica.api.repositories.CourseRepository;
import com.avaliacaopratica.api.repositories.RegistrationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RegistrationBusiness {

    private final RegistrationRepository registrationRepository;
    private final RegistrationMapper registrationMapper;
    private final CourseRepository courseRepository;
    private final RegistrationProducer registrationProducer;

    public void create(RegistrationRequestDTO request) {
        Course course = findCourseOrThrow(request.getIdCourse());
        validate(course, request);
        Registration registration = registrationMapper.toEntity(request);
        registration.setCourse(course);
        registrationRepository.save(registration);
    }

    public List<RegisteredResponseDTO> findInscritosByCurso(Integer idCurso) {
        return registrationRepository.findInscritosByCurso(idCurso);
    }

    public void enfileirarFinalizacao(FinishRegistrationRequestDTO request) {
        Course course = findCourseOrThrow(request.getIdCourse());
        if (!course.isEmAndamento()) {
            throw new BusinessException("Curso não está em andamento");
        }
        registrationProducer.enviarParaFila(request.getIdCourse());
    }

    public void finalizarInscricoesCurso(Integer idCurso) {
        log.info("Iniciando finalização das inscrições do curso {}", idCurso);
        Course course = findCourseOrThrow(idCurso);
        List<Registration> registrations = registrationRepository.findByCourse_IdCourseOrderByScoreDesc(idCurso);

        int numVacancies = course.getNumVacancies();
        for (int i = 0; i < registrations.size(); i++) {
            registrations.get(i).setRegistrationStatus(i < numVacancies ? RegistrationStatus.SELECIONADO : RegistrationStatus.NAO_SELECIONADO);
        }

        course.setCourseRegistrationStatus(CourseRegistrationStatus.FINALIZADA);

        registrationRepository.saveAll(registrations);
        courseRepository.save(course);
        log.info("Finalização concluída para o curso {}. Vagas: {}, Inscritos: {}", idCurso, numVacancies, registrations.size());
    }

    public List<RegisteredResponseDTO> findInscritosFinalizadosByCurso(Integer idCurso) {
        return registrationRepository.findInscritosFinalizadosByCurso(idCurso);
    }

    private Course findCourseOrThrow(Integer idCurso) {
        return courseRepository.findById(idCurso).orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));
    }

    private void validate(Course course, RegistrationRequestDTO request) {
        if (!course.isEmAndamento()) {
            throw new BusinessException("Curso não está em andamento");
        }

        if (registrationRepository.existsByCourse_IdCourseAndCpf(request.getIdCourse(), request.getCpf())) {
            throw new BusinessException("Pessoa já está inscrita neste curso");
        }
    }
}