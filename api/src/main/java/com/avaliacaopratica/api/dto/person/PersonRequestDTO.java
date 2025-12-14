package com.avaliacaopratica.api.dto.person;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    private String cpf;

    private String email;

}
