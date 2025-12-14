package com.avaliacaopratica.backend.dto.address;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDTO {

    private Integer id;

    private String cep;

    private String rua;

    private String numero;

    private String cidade;

    private String uf;

}
