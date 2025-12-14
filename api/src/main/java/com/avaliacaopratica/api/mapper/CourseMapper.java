package com.avaliacaopratica.api.mapper;

import com.avaliacaopratica.api.models.Course;
import com.avaliacaopratica.api.dto.CourseRequestDTO;
import com.avaliacaopratica.api.dto.CourseResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseMapper {

    Course toEntity(CourseRequestDTO dto);

    CourseResponseDTO toResponse(Course entity);
}
