package com.avaliacaopratica.backend.dto.person;

import com.avaliacaopratica.backend.dto.address.AddressResponseDTO;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponseDTO {

    private Integer idPerson;

    private String name;

    private LocalDate birth;

    private String cpf;

    private String email;

    private String integrationStatus;

    private String mensagem;

    private AddressResponseDTO address;

}
