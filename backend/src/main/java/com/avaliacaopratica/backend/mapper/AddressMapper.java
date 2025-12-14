package com.avaliacaopratica.backend.mapper;

import com.avaliacaopratica.backend.dto.address.AddressResponseDTO;
import com.avaliacaopratica.backend.models.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {

    AddressResponseDTO toResponse(Address address);

}
