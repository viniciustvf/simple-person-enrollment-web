package com.avaliacaopratica.backend.messaging.consumer;

import com.avaliacaopratica.backend.config.RabbitMQConfig;
import com.avaliacaopratica.backend.dto.person.IntegratePersonRequestDTO;
import com.avaliacaopratica.backend.enums.IntegrationStatus;
import com.avaliacaopratica.backend.messaging.strategy.PersonIntegrationStrategyFactory;
import com.avaliacaopratica.backend.repositories.PersonRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PersonConsumer {

    private final PersonIntegrationStrategyFactory personIntegrationStrategyFactory;
    private final PersonRepository personRepository;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    @Transactional
    public void processar(IntegratePersonRequestDTO dto) {
        log.info("Consumindo pessoa {} - ação {}", dto.getCpf(), dto.getAction());

        try {
            personIntegrationStrategyFactory.getStrategy(dto.getAction().getName()).execute(dto);
            log.info("Integração concluída com sucesso para {}", dto.getCpf());
        } catch (Exception ex) {
            log.error("Erro na integração da pessoa {}: {}", dto.getCpf(), ex.getMessage());
            personRepository.findByCpf(dto.getCpf()).ifPresent(person -> {
                person.setIntegrationStatus(IntegrationStatus.ERRO);
                personRepository.save(person);
            });
        }
    }
}
