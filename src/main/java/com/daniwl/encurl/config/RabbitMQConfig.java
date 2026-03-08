package com.daniwl.encurl.config;

import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    /**
     * Define o JSON como formato padrão de mensagens no RabbitMQ.
     * Utiliza JacksonJsonMessageConverter para compatibilidade total com Jackson 3 e Spring 4.0+.
     */
    @Bean
    public JacksonJsonMessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
