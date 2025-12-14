package com.avaliacaopratica.api.messaging.producer;

import com.avaliacaopratica.api.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationProducer {

    private final RabbitTemplate rabbitTemplate;

    public void enviarParaFila(Integer idCurso) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                idCurso
        );
    }
}

