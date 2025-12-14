package com.avaliacaopratica.api.business;

import com.avaliacaopratica.api.enums.CourseRegistrationStatus;
import com.avaliacaopratica.api.mapper.CourseMapper;
import com.avaliacaopratica.api.models.Course;
import com.avaliacaopratica.api.dto.CourseRequestDTO;
import com.avaliacaopratica.api.dto.CourseResponseDTO;
import com.avaliacaopratica.api.repositories.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class CourseBusiness {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseResponseDTO create(CourseRequestDTO request) {
        Course entity = courseMapper.toEntity(request);
        entity.setSituacaoCurso(CourseRegistrationStatus.EM_ANDAMENTO);

        courseRepository.save(entity);
        return courseMapper.toResponse(entity);
    }

    public CourseResponseDTO update(Integer id, CourseRequestDTO request) {
        Course entity = findEntityById(id);

        entity.setNome(request.getNome());
        entity.setNumeroVagas(request.getNumeroVagas());

        courseRepository.save(entity);

        return courseMapper.toResponse(entity);
    }

    public CourseResponseDTO findById(Integer id) {
        return courseMapper.toResponse(findEntityById(id));
    }

    public List<CourseResponseDTO> findAll() {
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toResponse)
                .toList();
    }

    public Page<CourseResponseDTO> findAllPaginated(Pageable pageable) {
        return courseRepository.findAll(pageable)
                .map(courseMapper::toResponse);
    }

    public void delete(Integer id) {
        Course entity = findEntityById(id);
        courseRepository.delete(entity);
    }

    private Course findEntityById(Integer id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));
    }
}
