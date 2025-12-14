package com.avaliacaopratica.backend.dto.person;

import com.avaliacaopratica.backend.enums.IntegrationAction;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IntegratePersonRequestDTO {

    private String name;

    private String cpf;

    private String email;

    private IntegrationAction action;

}
