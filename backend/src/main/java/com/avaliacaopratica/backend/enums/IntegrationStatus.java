package com.avaliacaopratica.backend.enums;

public enum IntegrationStatus {

    SUCESSO("Sucesso"),
    ERRO("Erro"),
    NAO_ENVIADO("Não enviado"),
    PENDENTE("Pendente");

    final String string;

    IntegrationStatus(String str) {
        this.string = str;
    }

    @Override
    public String toString() {
        return string;
    }
}