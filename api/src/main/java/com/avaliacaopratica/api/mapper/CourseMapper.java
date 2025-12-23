package com.avaliacaopratica.api.mapper;

import com.avaliacaopratica.api.dto.course.CoursePostResponseDTO;
import com.avaliacaopratica.api.models.Course;
import com.avaliacaopratica.api.dto.course.CourseRequestDTO;
import com.avaliacaopratica.api.dto.course.CourseResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseMapper {

    Course toEntity(CourseRequestDTO dto);

    void toEntity(CourseRequestDTO dto, @MappingTarget Course entity);

    CourseResponseDTO toResponse(Course entity);

    @Mapping(target = "mensagem", constant = "Curso criado com sucesso")
    CoursePostResponseDTO toPostResponse(Course entity);

}
