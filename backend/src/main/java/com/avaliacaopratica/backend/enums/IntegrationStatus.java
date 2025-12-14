package com.avaliacaopratica.backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IntegrationStatus {

    SUCESSO("Sucesso"),
    ERRO("Erro"),
    NAO_ENVIADO("Não enviado"),
    PENDENTE("Pendente");

    final String string;

    @Override
    public String toString() {
        return string;
    }
}