package com.avaliacaopratica.backend.mapper;

import com.avaliacaopratica.backend.dto.address.AddressRequestDTO;
import com.avaliacaopratica.backend.dto.address.AddressResponseDTO;
import com.avaliacaopratica.backend.models.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {

    void toEntity(AddressRequestDTO dto, @MappingTarget Address entity);

    AddressResponseDTO toResponse(Address address);

}
