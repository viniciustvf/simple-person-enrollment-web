package com.avaliacaopratica.api.mapper;

import com.avaliacaopratica.api.models.Registration;
import com.avaliacaopratica.api.dto.RegistrationRequestDTO;
import com.avaliacaopratica.api.dto.RegistrationResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RegistrationMapper {

    @Mapping(target = "course", ignore = true)
    @Mapping(target = "situacaoInscricao", ignore = true)
    Registration toEntity(RegistrationRequestDTO dto);

    @Mapping(source = "course.idCourse", target = "idCourse")
    @Mapping(source = "course.nome", target = "nomeCurso")
    RegistrationResponseDTO toResponse(Registration entity);
}

