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
    //Company register consumer

    String userCompanyRegisterQueue = "user-company-register-queue";

    @Bean
    Queue userRegisterQueue() {
        return new Queue(userCompanyRegisterQueue);
    }

    // auth forgot password
    private String forgotPassQueue = "forgot-pass-queue";
    @Bean
    Queue forgotPassQueue(){
        return new Queue(forgotPassQueue);
    }

}
