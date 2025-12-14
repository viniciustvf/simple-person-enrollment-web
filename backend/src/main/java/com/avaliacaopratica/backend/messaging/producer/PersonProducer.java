package com.avaliacaopratica.backend.messaging.producer;

import com.avaliacaopratica.backend.config.RabbitMQConfig;
import com.avaliacaopratica.backend.dto.person.IntegratePersonRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonProducer {

    private final RabbitTemplate rabbitTemplate;

    public void enviarParaFila(IntegratePersonRequestDTO dto) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, dto);
    }
}
