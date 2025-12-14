package com.avaliacaopratica.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IntegratePersonRequestDTO {

    private String name;

    private String cpf;

    private String email;
}
