package com.bilgeadam.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    //Auth register Consumer
    String authRegisterQueue = "auth-register-queue";

    @Bean
    Queue authRegisterQueue(){
        return new Queue(authRegisterQueue);
    }

}
