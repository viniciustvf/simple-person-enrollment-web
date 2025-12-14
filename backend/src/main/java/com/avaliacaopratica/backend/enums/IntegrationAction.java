package com.avaliacaopratica.backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IntegrationAction {

    CREATE_UPDATE("create_update"),
    DELETE("delete");

    final String name;

}
