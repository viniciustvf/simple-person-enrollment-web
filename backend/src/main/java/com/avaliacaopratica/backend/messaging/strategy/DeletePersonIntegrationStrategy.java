package com.avaliacaopratica.backend.messaging.strategy;

import com.avaliacaopratica.backend.dto.person.IntegratePersonRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("delete")
@RequiredArgsConstructor
public class DeletePersonIntegrationStrategy implements PersonIntegrationStrategy {

    private final RestTemplate restTemplate;

    @Value("${api.url}")
    private String apiUrl;

    private static final String DELETE_PERSON_PATH = "/v1/person/cpf/{cpf}";

    @Override
    public void execute(IntegratePersonRequestDTO dto) {
        restTemplate.delete(apiUrl + DELETE_PERSON_PATH, dto.getCpf());
    }
}
