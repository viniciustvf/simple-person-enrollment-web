package com.avaliacaopratica.backend.messaging.consumer;

import com.avaliacaopratica.backend.config.RabbitMQConfig;
import com.avaliacaopratica.backend.dto.IntegratePersonRequestDTO;
import com.avaliacaopratica.backend.enums.IntegrationStatus;
import com.avaliacaopratica.backend.models.Person;
import com.avaliacaopratica.backend.repositories.PersonRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
@RequiredArgsConstructor
public class PersonConsumer {

    private final RestTemplate restTemplate = new RestTemplate();
    private final PersonRepository personRepository;

    private final String apiUrl = "http://localhost:8081";

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    @Transactional
    public void processar(IntegratePersonRequestDTO dto) {
        log.info("Consumindo pessoa {} da fila para integração", dto.getCpf());

        Person person = personRepository.findByCpf(dto.getCpf()).orElse(null);

        if (person == null) {
            log.warn("Pessoa com CPF {} não encontrada no banco", dto.getCpf());
            return;
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<IntegratePersonRequestDTO> request =
                    new HttpEntity<>(dto, headers);

            restTemplate.postForEntity(apiUrl + "/person", request, Void.class);

            person.setIntegrationStatus(IntegrationStatus.SUCESSO);
            log.info("Pessoa {} integrada com sucesso", dto.getCpf());

        } catch (RestClientException ex) {
            person.setIntegrationStatus(IntegrationStatus.ERRO);
            log.error("Erro ao integrar pessoa {}: {}", dto.getCpf(), ex.getMessage());
        }

        personRepository.save(person);
    }
}
