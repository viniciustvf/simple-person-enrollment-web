package com.avaliacaopratica.api.business;

import com.avaliacaopratica.api.dto.course.CoursePostResponseDTO;
import com.avaliacaopratica.api.enums.CourseRegistrationStatus;
import com.avaliacaopratica.api.mapper.CourseMapper;
import com.avaliacaopratica.api.models.Course;
import com.avaliacaopratica.api.dto.course.CourseRequestDTO;
import com.avaliacaopratica.api.dto.course.CourseResponseDTO;
import com.avaliacaopratica.api.repositories.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseBusiness {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CoursePostResponseDTO create(CourseRequestDTO request) {
        Course entity = courseMapper.toEntity(request);
        entity.setCourseRegistrationStatus(CourseRegistrationStatus.EM_ANDAMENTO);
        courseRepository.save(entity);
        return courseMapper.toPostResponse(entity);
    }

    public CourseResponseDTO update(Integer id, CourseRequestDTO request) {
        Course entity = findByIdOrThrow(id);
        entity.setName(request.getName());
        entity.setNumVacancies(request.getNumVacancies());
        courseRepository.save(entity);
        return courseMapper.toResponse(entity);
    }

    public CourseResponseDTO findById(Integer id) {
        return courseMapper.toResponse(findByIdOrThrow(id));
    }

    public List<CourseResponseDTO> findAll() {
        return courseRepository.findAll()
                .stream()
                .map(courseMapper::toResponse)
                .toList();
    }

    public void delete(Integer id) {
        Course entity = findByIdOrThrow(id);
        courseRepository.delete(entity);
    }

    private Course findByIdOrThrow(Integer id) {
        return courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Curso não encontrado"));
    }
}
