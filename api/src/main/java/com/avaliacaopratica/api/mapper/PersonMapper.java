package com.avaliacaopratica.api.mapper;

import com.avaliacaopratica.api.dto.person.IntegratePersonRequestDTO;
import com.avaliacaopratica.api.models.Person;
import com.avaliacaopratica.api.dto.person.PersonRequestDTO;
import com.avaliacaopratica.api.dto.person.PersonResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PersonMapper {

    PersonResponseDTO toResponse(Person person);

}
