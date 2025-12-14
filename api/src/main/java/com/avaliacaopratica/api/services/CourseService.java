package com.avaliacaopratica.api.services;

import com.avaliacaopratica.api.dto.CourseRequestDTO;
import com.avaliacaopratica.api.dto.CourseResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {

    CourseResponseDTO createCourse(CourseRequestDTO request);

    CourseResponseDTO updateCourse(Integer id, CourseRequestDTO request);

    CourseResponseDTO findById(Integer id);

    List<CourseResponseDTO> findAll();

    Page<CourseResponseDTO> findAllPaginated(Pageable pageable);

    void deleteCourse(Integer id);
}
