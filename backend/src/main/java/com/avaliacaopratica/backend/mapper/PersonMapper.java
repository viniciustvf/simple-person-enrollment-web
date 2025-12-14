package com.avaliacaopratica.backend.mapper;

import com.avaliacaopratica.backend.dto.IntegratePersonRequestDTO;
import com.avaliacaopratica.backend.dto.PersonRequestDTO;
import com.avaliacaopratica.backend.dto.PersonResponseDTO;
import com.avaliacaopratica.backend.models.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = AddressMapper.class)
public interface PersonMapper {

    Person toEntity(PersonRequestDTO dto);

    @Mapping(target = "integrationStatus", expression = "java(person.getIntegrationStatus().toString())")
    @Mapping(target = "mensagem", constant = "Operação realizada com sucesso")
    PersonResponseDTO toResponse(Person person);

    IntegratePersonRequestDTO toIntegrate(Person person);

}