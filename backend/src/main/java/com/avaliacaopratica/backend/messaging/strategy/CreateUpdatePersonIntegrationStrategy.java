package com.avaliacaopratica.backend.messaging.strategy;

import com.avaliacaopratica.backend.dto.person.IntegratePersonRequestDTO;
import com.avaliacaopratica.backend.enums.IntegrationStatus;
import com.avaliacaopratica.backend.models.Person;
import com.avaliacaopratica.backend.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("create_update")
@RequiredArgsConstructor
public class CreateUpdatePersonIntegrationStrategy implements PersonIntegrationStrategy {

    private final RestTemplate restTemplate;
    private final PersonRepository personRepository;

    @Value("${api.url}")
    private String apiUrl;

    private static final String PERSON_PATH = "/v1/person";

    @Override
    public void execute(IntegratePersonRequestDTO dto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<IntegratePersonRequestDTO> request =
                new HttpEntity<>(dto, headers);

        restTemplate.postForEntity(apiUrl + PERSON_PATH, request, Void.class);

        Person person = personRepository.findByCpf(dto.getCpf()).orElse(null);
        if (person != null) {
            person.setIntegrationStatus(IntegrationStatus.SUCESSO);
            personRepository.save(person);
        }
    }
}
