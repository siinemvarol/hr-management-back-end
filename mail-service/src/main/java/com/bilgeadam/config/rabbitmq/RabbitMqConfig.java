package com.bilgeadam.config.rabbitmq;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

public class RabbitMqConfig {
    //Consumer

    //mail register consumer
    String mailRegisterQueue ="mail-register-queue";
    @Bean
    Queue mailRegisterQueue(){
        return new Queue(mailRegisterQueue);
    }

    //mail forgot pass consumer
    String mailForgotPasswordQueue = "mail-forgot-password-queue";
    @Bean
    Queue mailForgotPasswordQueue(){
        return new Queue(mailForgotPasswordQueue);
    }

    //Guest mail register consumer
    private final String guestMailRegisterQueue = "guest-mail-register-queue";
    @Bean
    Queue guestMailRegisterQueue(){
        return new Queue(guestMailRegisterQueue);
    }


}
