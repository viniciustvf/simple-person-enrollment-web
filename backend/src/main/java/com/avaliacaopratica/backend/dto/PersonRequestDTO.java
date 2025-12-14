package com.avaliacaopratica.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Data
public class PersonRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    private LocalDate birth;

    private String cpf;

    private String email;

    private AddressRequestDTO address;

    @Data
    public static class AddressRequestDTO {
        private String cep;
        private String rua;
        private String numero;
        private String cidade;
        private String uf;
    }
}
