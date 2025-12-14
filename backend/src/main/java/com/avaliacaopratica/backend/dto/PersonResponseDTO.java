package com.avaliacaopratica.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddressResponseDTO {

        private Integer id;

        private String cep;

        private String rua;

        private String numero;

        private String cidade;

        private String uf;

    }

}
