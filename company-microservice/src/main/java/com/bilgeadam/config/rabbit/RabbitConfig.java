package com.bilgeadam.config.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    private final String exchange = "company-exchange";

    @Bean
    DirectExchange companyExchange(){
        return new DirectExchange(exchange);
    }
    //register producer
    private final String userCompanyRegisterQueue = "user-company-register-queue";
    private final String userCompanyRegisterBinding = "user-company-register-binding";

    @Bean
    Queue userRegisterQueue() {
        return new Queue(userCompanyRegisterQueue);
    }

    @Bean
    public Binding userRegisterBinding(final DirectExchange authExchange,
                                       final Queue userRegisterQueue) {
        return BindingBuilder.bind(userRegisterQueue)
                .to(authExchange)
                .with(userCompanyRegisterBinding);
    }
}
