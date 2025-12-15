package com.avaliacaopratica.api.business;

import com.avaliacaopratica.api.dto.course.CoursePostResponseDTO;
import com.avaliacaopratica.api.dto.course.CourseRequestDTO;
import com.avaliacaopratica.api.dto.course.CourseResponseDTO;
import com.avaliacaopratica.api.enums.CourseRegistrationStatus;
import com.avaliacaopratica.api.mapper.CourseMapper;
import com.avaliacaopratica.api.models.Course;
import com.avaliacaopratica.api.repositories.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseBusinessTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseBusiness courseBusiness;

    @Test
    void deveCriarCursoComStatusEmAndamento() {
        CourseRequestDTO request = mock(CourseRequestDTO.class);
        Course entity = new Course();
        CoursePostResponseDTO response = mock(CoursePostResponseDTO.class);

        when(courseMapper.toEntity(request)).thenReturn(entity);
        when(courseMapper.toPostResponse(entity)).thenReturn(response);

        CoursePostResponseDTO result = courseBusiness.create(request);

        assertSame(response, result);
        assertEquals(CourseRegistrationStatus.EM_ANDAMENTO, entity.getCourseRegistrationStatus());
        verify(courseRepository).save(entity);
        verify(courseMapper).toEntity(request);
        verify(courseMapper).toPostResponse(entity);
        verifyNoMoreInteractions(courseRepository, courseMapper);
    }

    @Test
    void deveAtualizarCursoQuandoEncontrado() {
        Integer id = 10;

        CourseRequestDTO request = mock(CourseRequestDTO.class);
        when(request.getName()).thenReturn("Novo Nome");
        when(request.getNumVacancies()).thenReturn(25);

        Course entity = new Course();
        entity.setName("Antigo Nome");
        entity.setNumVacancies(10);

        CourseResponseDTO response = mock(CourseResponseDTO.class);

        when(courseRepository.findById(id)).thenReturn(Optional.of(entity));
        when(courseMapper.toResponse(entity)).thenReturn(response);

        CourseResponseDTO result = courseBusiness.update(id, request);

        assertSame(response, result);
        assertEquals("Novo Nome", entity.getName());
        assertEquals(25, entity.getNumVacancies());
        verify(courseRepository).save(entity);
    }

    @Test
    void deveLancarExcecaoAoAtualizarCursoInexistente() {
        Integer id = 99;
        CourseRequestDTO request = mock(CourseRequestDTO.class);

        when(courseRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> courseBusiness.update(id, request));
        verify(courseRepository, never()).save(any());
    }

    @Test
    void deveBuscarCursoPorId() {
        Integer id = 1;
        Course entity = new Course();
        CourseResponseDTO response = mock(CourseResponseDTO.class);

        when(courseRepository.findById(id)).thenReturn(Optional.of(entity));
        when(courseMapper.toResponse(entity)).thenReturn(response);

        CourseResponseDTO result = courseBusiness.findById(id);

        assertSame(response, result);
        verify(courseRepository).findById(id);
        verify(courseMapper).toResponse(entity);
    }

    @Test
    void deveListarTodosOsCursos() {
        Course c1 = new Course();
        Course c2 = new Course();

        CourseResponseDTO r1 = mock(CourseResponseDTO.class);
        CourseResponseDTO r2 = mock(CourseResponseDTO.class);

        when(courseRepository.findAll()).thenReturn(List.of(c1, c2));
        when(courseMapper.toResponse(c1)).thenReturn(r1);
        when(courseMapper.toResponse(c2)).thenReturn(r2);

        List<CourseResponseDTO> result = courseBusiness.findAll();

        assertEquals(2, result.size());
        assertEquals(List.of(r1, r2), result);
        verify(courseRepository).findAll();
        verify(courseMapper).toResponse(c1);
        verify(courseMapper).toResponse(c2);
    }

    @Test
    void deveDeletarCursoQuandoEncontrado() {
        Integer id = 7;
        Course entity = new Course();

        when(courseRepository.findById(id)).thenReturn(Optional.of(entity));

        courseBusiness.delete(id);

        verify(courseRepository).delete(entity);
    }

    @Test
    void deveLancarExcecaoAoDeletarCursoInexistente() {
        Integer id = 123;
        when(courseRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> courseBusiness.delete(id));
        verify(courseRepository, never()).delete(any());
    }
}
