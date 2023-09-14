package com.bilgeadam.config.rabbitmq;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    private String exchange = "comment-exchange";
    @Bean
    DirectExchange commentExchange(){
        return new DirectExchange(exchange);
    }
}
