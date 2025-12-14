package com.avaliacaopratica.api.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "registration.finalizar";
    public static final String ROUTING_KEY = "registration.finalizar";
    public static final String QUEUE = "registration.finalizar.queue";

    @Bean
    TopicExchange registrationExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE).durable(true).build();
    }

    @Bean
    Queue registrationQueue() {
        return QueueBuilder.durable(QUEUE).build();
    }

    @Bean
    Binding registrationBinding() {
        return BindingBuilder
                .bind(registrationQueue())
                .to(registrationExchange())
                .with(ROUTING_KEY);
    }
}