package com.avaliacaopratica.api.business;

import com.avaliacaopratica.api.dto.registration.FinishRegistrationRequestDTO;
import com.avaliacaopratica.api.dto.registration.RegisteredResponseDTO;
import com.avaliacaopratica.api.dto.registration.RegistrationRequestDTO;
import com.avaliacaopratica.api.enums.CourseRegistrationStatus;
import com.avaliacaopratica.api.enums.RegistrationStatus;
import com.avaliacaopratica.api.exceptions.BusinessException;
import com.avaliacaopratica.api.mapper.RegistrationMapper;
import com.avaliacaopratica.api.messaging.producer.RegistrationProducer;
import com.avaliacaopratica.api.models.Course;
import com.avaliacaopratica.api.models.Registration;
import com.avaliacaopratica.api.repositories.CourseRepository;
import com.avaliacaopratica.api.repositories.RegistrationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationBusinessTest {

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private RegistrationMapper registrationMapper;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private RegistrationProducer registrationProducer;

    @InjectMocks
    private RegistrationBusiness registrationBusiness;

    @Test
    void deveCriarInscricaoQuandoCursoEmAndamentoECPFNaoInscrito() {
        RegistrationRequestDTO request = mock(RegistrationRequestDTO.class);
        when(request.getIdCourse()).thenReturn(1);
        when(request.getCpf()).thenReturn("123");

        Course course = mock(Course.class);
        when(course.isEmAndamento()).thenReturn(true);

        Registration entity = new Registration();

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(registrationRepository.existsByCourse_IdCourseAndCpf(1, "123")).thenReturn(false);
        when(registrationMapper.toEntity(request)).thenReturn(entity);

        registrationBusiness.create(request);

        assertSame(course, entity.getCourse());
        verify(registrationRepository).save(entity);
    }

    @Test
    void deveLancarExcecaoAoCriarInscricaoQuandoCursoNaoExiste() {
        RegistrationRequestDTO request = mock(RegistrationRequestDTO.class);
        when(request.getIdCourse()).thenReturn(1);

        when(courseRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> registrationBusiness.create(request));
        verify(registrationRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoAoCriarInscricaoQuandoCursoNaoEstaEmAndamento() {
        RegistrationRequestDTO request = mock(RegistrationRequestDTO.class);
        when(request.getIdCourse()).thenReturn(1);

        Course course = mock(Course.class);
        when(course.isEmAndamento()).thenReturn(false);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        assertThrows(BusinessException.class, () -> registrationBusiness.create(request));
        verify(registrationRepository, never()).save(any());
    }

    @Test
    void deveLancarExcecaoAoCriarInscricaoQuandoPessoaJaInscritaNoCurso() {
        RegistrationRequestDTO request = mock(RegistrationRequestDTO.class);
        when(request.getIdCourse()).thenReturn(1);
        when(request.getCpf()).thenReturn("123");

        Course course = mock(Course.class);
        when(course.isEmAndamento()).thenReturn(true);

        when(courseRepository.findById(1)).thenReturn(Optional.of(course));
        when(registrationRepository.existsByCourse_IdCourseAndCpf(1, "123")).thenReturn(true);

        assertThrows(BusinessException.class, () -> registrationBusiness.create(request));
        verify(registrationRepository, never()).save(any());
    }

    @Test
    void deveBuscarInscritosPorCurso() {
        List<RegisteredResponseDTO> list = List.of(mock(RegisteredResponseDTO.class));
        when(registrationRepository.findInscritosByCurso(1)).thenReturn(list);

        List<RegisteredResponseDTO> result = registrationBusiness.findInscritosByCurso(1);

        assertSame(list, result);
        verify(registrationRepository).findInscritosByCurso(1);
    }

    @Test
    void deveEnfileirarFinalizacaoQuandoCursoEmAndamento() {
        Course course = mock(Course.class);
        when(course.isEmAndamento()).thenReturn(true);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        FinishRegistrationRequestDTO request = new FinishRegistrationRequestDTO();
        request.setIdCourse(1);

        registrationBusiness.enfileirarFinalizacao(request);

        verify(registrationProducer).enviarParaFila(1);
    }

    @Test
    void deveLancarExcecaoAoEnfileirarFinalizacaoQuandoCursoNaoEstaEmAndamento() {
        Course course = mock(Course.class);
        when(course.isEmAndamento()).thenReturn(false);
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        FinishRegistrationRequestDTO request = new FinishRegistrationRequestDTO();
        request.setIdCourse(1);

        assertThrows(BusinessException.class, () -> registrationBusiness.enfileirarFinalizacao(request));
        verify(registrationProducer, never()).enviarParaFila(anyInt());
    }

    @Test
    void deveFinalizarInscricoesMarcandoSelecionadosEAtualizandoStatusDoCurso() {
        int idCurso = 1;

        Course course = mock(Course.class);
        when(course.getNumVacancies()).thenReturn(2);

        Registration r1 = new Registration(); r1.setScore(9.0);
        Registration r2 = new Registration(); r2.setScore(8.0);
        Registration r3 = new Registration(); r3.setScore(7.0);

        List<Registration> registrations = new ArrayList<>(List.of(r2, r3, r1));

        when(courseRepository.findById(idCurso)).thenReturn(Optional.of(course));
        when(registrationRepository.findByCourse_IdCourseOrderByScoreDesc(idCurso)).thenReturn(registrations);

        registrationBusiness.finalizarInscricoesCurso(idCurso);

        assertEquals(RegistrationStatus.SELECIONADO, registrations.get(0).getRegistrationStatus());
        assertEquals(RegistrationStatus.SELECIONADO, registrations.get(1).getRegistrationStatus());
        assertEquals(RegistrationStatus.NAO_SELECIONADO, registrations.get(2).getRegistrationStatus());

        verify(course).setCourseRegistrationStatus(CourseRegistrationStatus.FINALIZADA);
        verify(registrationRepository).saveAll(registrations);
        verify(courseRepository).save(course);
    }

    @Test
    void deveBuscarInscritosFinalizadosPorCurso() {
        List<RegisteredResponseDTO> list = List.of(mock(RegisteredResponseDTO.class));
        when(registrationRepository.findInscritosFinalizadosByCurso(1)).thenReturn(list);

        List<RegisteredResponseDTO> result = registrationBusiness.findInscritosFinalizadosByCurso(1);

        assertSame(list, result);
        verify(registrationRepository).findInscritosFinalizadosByCurso(1);
    }
}