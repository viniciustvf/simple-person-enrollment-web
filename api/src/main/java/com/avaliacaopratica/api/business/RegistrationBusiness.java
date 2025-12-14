package com.avaliacaopratica.api.business;

import com.avaliacaopratica.api.exceptions.BusinessException;
import com.avaliacaopratica.api.enums.CourseRegistrationStatus;
import com.avaliacaopratica.api.enums.RegistrationStatus;
import com.avaliacaopratica.api.mapper.RegistrationMapper;
import com.avaliacaopratica.api.messaging.producer.RegistrationProducer;
import com.avaliacaopratica.api.models.Course;
import com.avaliacaopratica.api.models.Registration;
import com.avaliacaopratica.api.dto.registration.RegisteredResponseDTO;
import com.avaliacaopratica.api.dto.registration.RegistrationRequestDTO;
import com.avaliacaopratica.api.dto.registration.RegistrationResponseDTO;
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

    @Transactional
    public void create(RegistrationRequestDTO request) {
        Course course = courseRepository.findById(request.getIdCourse()).orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));
        validate(course, request);
        Registration registration = registrationMapper.toEntity(request);
        registration.setCourse(course);
        registrationRepository.save(registration);
    }

    public List<RegisteredResponseDTO> findInscritosByCurso(Integer idCurso) {
        return registrationRepository.findInscritosByCurso(idCurso);
    }

    public void enfileirarFinalizacao(Integer idCurso) {
        Course course = courseRepository.findById(idCurso).orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));
        if (!course.isEmAndamento()) {
            throw new BusinessException("Curso não está em andamento");
        }
        registrationProducer.enviarParaFila(idCurso);
    }

    @Transactional
    public void finalizarInscricoesCurso(Integer idCurso) {
        log.info("Iniciando finalização das inscrições do curso {}", idCurso);
        Course course = courseRepository.findById(idCurso).orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));
        List<Registration> registrations = registrationRepository.findByCourse_IdCourseOrderByScoreDesc(idCurso);

        registrations.sort((registrationA, registrationB) -> registrationB.getScore().compareTo(registrationA.getScore()));
        int numVacancies = course.getNumVacancies();
        for (int i = 0; i < registrations.size(); i++) {
            Registration registration = registrations.get(i);

            if (i < numVacancies) {
                registration.setRegistrationStatus(RegistrationStatus.SELECIONADO);
            } else {
                registration.setRegistrationStatus(RegistrationStatus.NAO_SELECIONADO);
            }
        }

        course.setCourseRegistrationStatus(CourseRegistrationStatus.FINALIZADA);

        registrationRepository.saveAll(registrations);
        courseRepository.save(course);
        log.info("Finalização concluída para o curso {}. Vagas: {}, Inscritos: {}", idCurso, numVacancies, registrations.size());
    }

    public List<RegisteredResponseDTO> findInscritosFinalizadosByCurso(Integer idCurso) {
        return registrationRepository.findInscritosFinalizadosByCurso(idCurso);
    }

    private void validate(Course course, RegistrationRequestDTO request) {
        if (!course.isEmAndamento()) {
            throw new BusinessException("Curso não está em andamento");
        }

        boolean jaInscrito = registrationRepository.existsByCourse_IdCourseAndCpf(request.getIdCourse(), request.getCpf());

        if (jaInscrito) {
            throw new BusinessException("Pessoa já está inscrita neste curso");
        }
    }
}
