package com.avaliacaopratica.backend.messaging.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PersonIntegrationStrategyFactory {

    private final Map<String, PersonIntegrationStrategy> strategies;

    public PersonIntegrationStrategy getStrategy(String action) {
        PersonIntegrationStrategy strategy = strategies.get(action);

        if (strategy == null) {
            throw new IllegalArgumentException("Ação de integração não suportada: " + action);
        }

        return strategy;
    }
}
