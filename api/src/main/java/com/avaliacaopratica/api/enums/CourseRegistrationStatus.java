package com.avaliacaopratica.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CourseRegistrationStatus {

    EM_ANDAMENTO("Em andamento"),
    FINALIZADA("Finalizada");

    private final String descricao;

    @Override
    public String toString() {
        return descricao;
    }
}
