package com.avaliacaopratica.api.dto.registration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequestDTO {

    @NotNull
    private Integer idCourse;

    @NotBlank
    private String cpf;

    @NotNull
    private Double nota;

}

