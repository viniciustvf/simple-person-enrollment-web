package com.avaliacaopratica.api.enums;

public enum CourseRegistrationStatus {

    EM_ANDAMENTO("Em andamento"),
    FINALIZADA("Finalizada");

    private final String descricao;

    CourseRegistrationStatus(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
