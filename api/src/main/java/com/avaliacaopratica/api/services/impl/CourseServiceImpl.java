package com.avaliacaopratica.api.services.impl;

import com.avaliacaopratica.api.business.CourseBusiness;
import com.avaliacaopratica.api.dto.CourseRequestDTO;
import com.avaliacaopratica.api.dto.CourseResponseDTO;
import com.avaliacaopratica.api.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseBusiness courseBusiness;

    @Override
    public CourseResponseDTO createCourse(CourseRequestDTO request) {
        return courseBusiness.create(request);
    }

    @Override
    public CourseResponseDTO updateCourse(Integer id, CourseRequestDTO request) {
        return courseBusiness.update(id, request);
    }

    @Override
    public CourseResponseDTO findById(Integer id) {
        return courseBusiness.findById(id);
    }

    @Override
    public List<CourseResponseDTO> findAll() {
        return courseBusiness.findAll();
    }

    @Override
    public Page<CourseResponseDTO> findAllPaginated(Pageable pageable) {
        return courseBusiness.findAllPaginated(pageable);
    }

    @Override
    public void deleteCourse(Integer id) {
        courseBusiness.delete(id);
    }
}