package com.avaliacaopratica.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RegistrationStatus {

    SELECIONADO("Selecionado"),
    NAO_SELECIONADO("Não selecionado");

    private final String descricao;

    @Override
    public String toString() {
        return descricao;
    }
}
