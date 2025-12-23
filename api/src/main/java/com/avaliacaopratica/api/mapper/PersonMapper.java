package com.avaliacaopratica.api.mapper;

import com.avaliacaopratica.api.models.Person;
import com.avaliacaopratica.api.dto.person.PersonRequestDTO;
import com.avaliacaopratica.api.dto.person.PersonResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonMapper {

    Person toEntity(PersonRequestDTO dto);

    void toEntity(PersonRequestDTO dto, @MappingTarget Person entity);

    PersonResponseDTO toResponse(Person person);

}
