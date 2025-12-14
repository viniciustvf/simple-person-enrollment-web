package com.avaliacaopratica.api.mapper;

import com.avaliacaopratica.api.dto.IntegratePersonRequestDTO;
import com.avaliacaopratica.api.models.Person;
import com.avaliacaopratica.api.dto.PersonRequestDTO;
import com.avaliacaopratica.api.dto.PersonResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonMapper {

    Person toEntity(PersonRequestDTO dto);

    PersonResponseDTO toResponse(Person person);

    IntegratePersonRequestDTO toIntegrate(Person person);
}
