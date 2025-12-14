package com.avaliacaopratica.api.dto.person;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IntegratePersonRequestDTO {

    private String name;

    private String cpf;

    private String email;
}
