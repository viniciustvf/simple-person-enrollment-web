package com.avaliacaopratica.api.mapper;

import com.avaliacaopratica.api.models.Registration;
import com.avaliacaopratica.api.dto.registration.RegistrationRequestDTO;
import com.avaliacaopratica.api.dto.registration.RegistrationResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RegistrationMapper {

    @Mapping(target = "course", ignore = true)
    @Mapping(target = "registrationStatus", ignore = true)
    @Mapping(target = "idRegistration", ignore = true)
    @Mapping(target = "score", source = "nota")
    Registration toEntity(RegistrationRequestDTO dto);

    @Mapping(source = "course.idCourse", target = "idCourse")
    @Mapping(source = "course.name", target = "nomeCurso")
    RegistrationResponseDTO toResponse(Registration entity);

}

