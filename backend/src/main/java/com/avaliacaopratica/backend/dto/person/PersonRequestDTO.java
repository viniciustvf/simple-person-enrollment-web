package com.avaliacaopratica.backend.dto.person;

import com.avaliacaopratica.backend.dto.address.AddressRequestDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequestDTO {

    @NotBlank(message = "Nome é obrigatório.")
    private String name;

    @NotBlank(message = "CPF é obrigatório.")
    @CPF(message = "CPF inválido")
    private String cpf;

    @Email(message = "E-mail inválido.")
    private String email;

    @Past(message = "Data de nascimento deve ser no passado.")
    private LocalDate birth;

    private AddressRequestDTO address;
}
