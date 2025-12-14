package com.avaliacaopratica.backend.messaging.strategy;

import com.avaliacaopratica.backend.dto.person.IntegratePersonRequestDTO;

public interface PersonIntegrationStrategy {

    void execute(IntegratePersonRequestDTO dto);

}
