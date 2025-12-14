package com.avaliacaopratica.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PersonRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    private String cpf;

    private String email;

}
