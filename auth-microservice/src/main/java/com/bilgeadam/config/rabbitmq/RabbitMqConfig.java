package com.bilgeadam.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    private String exchange ="auth-exchange";
    @Bean
    DirectExchange authExchange(){
        return new DirectExchange(exchange);
    }


    //User register producer
    private String userRegisterQueue = "user-register-queue";

    @Bean
    Queue userRegisterQueue(){
        return new Queue(userRegisterQueue);
    }
    private String userRegisterBinding = "user-register-binding";

    @Bean
    public Binding userRegisterBinding(final Queue userRegisterQueue, final DirectExchange authExchange){
        return BindingBuilder.bind(userRegisterQueue()).to(authExchange).with(userRegisterBinding);
    }
}
