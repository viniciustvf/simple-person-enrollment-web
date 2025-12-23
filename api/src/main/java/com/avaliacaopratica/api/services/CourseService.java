package com.avaliacaopratica.api.services;

import com.avaliacaopratica.api.dto.course.CoursePostResponseDTO;
import com.avaliacaopratica.api.dto.course.CourseRequestDTO;
import com.avaliacaopratica.api.dto.course.CourseResponseDTO;

import java.util.List;

public interface CourseService {

    CoursePostResponseDTO createCourse(CourseRequestDTO request);
    CourseResponseDTO updateCourse(Integer id, CourseRequestDTO request);
    CourseResponseDTO findById(Integer id);
    List<CourseResponseDTO> findAll();
    void deleteCourse(Integer id);

}
