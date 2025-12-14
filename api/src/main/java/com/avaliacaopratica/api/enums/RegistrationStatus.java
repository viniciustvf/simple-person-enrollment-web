package com.avaliacaopratica.api.enums;

public enum RegistrationStatus {

    SELECIONADO("Selecionado"),
    NAO_SELECIONADO("Não selecionado");

    private final String descricao;

    RegistrationStatus(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
