package com.avaliacaopratica.api.messaging.consumer;

import com.avaliacaopratica.api.business.RegistrationBusiness;
import com.avaliacaopratica.api.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class RegistrationConsumer {

    private final RegistrationBusiness registrationBusiness;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void processar(Integer idCurso) {
        log.info("Mensagem recebida para finalização das inscrições do curso {}", idCurso);
        try {
            registrationBusiness.finalizarInscricoesCurso(idCurso);
            log.info("Finalização das inscrições concluída para o curso {}", idCurso);
        } catch (Exception ex) {
            log.error("Erro ao finalizar inscrições do curso {}: {}", idCurso, ex.getMessage(), ex);
        }
    }
}
