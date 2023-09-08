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
    public Binding userRegisterBinding(final DirectExchange companyExchange,
                                       final Queue userRegisterQueue) {
        return BindingBuilder.bind(userRegisterQueue)
                .to(companyExchange)
                .with(userCompanyRegisterBinding);
    }
    // UserCompanyId producer

    private final String userCompanyIdQueue = "user-company-id-queue";
    private final String userCompanyIdBinding = "user-company-id-binding";
    @Bean
    Queue userCompanyIdQueue() {
        return new Queue(userCompanyIdQueue);
    }

    @Bean
    public Binding userCompanyIdBinding(final DirectExchange companyExchange,
                                       final Queue userCompanyIdQueue) {
        return BindingBuilder.bind(userCompanyIdQueue)
                .to(companyExchange)
                .with(userCompanyIdBinding);
    }

    //AddEmployeeCompanyProducer
    private String addEmployeeCompanyQueue = "add-employee-company-queue";
    private String addEmployeeCompanyBinding = "add-employee-company-binding";

    @Bean
    Queue addEmployeeCompanyQueue(){
        return new Queue(addEmployeeCompanyQueue);
    }
    @Bean
    public Binding addEmployeeCompanyBinding(final Queue addEmployeeCompanyQueue, final DirectExchange directExchange){
        return BindingBuilder.bind(addEmployeeCompanyQueue).to(directExchange).with(addEmployeeCompanyBinding);
    }

    //Consumer

    String userListCompanyQueue = "userList-company-queue";

    @Bean
    Queue userListCompanyQueue(){
        return new Queue(userListCompanyQueue);
    }

}
