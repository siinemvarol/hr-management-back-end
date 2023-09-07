package com.bilgeadam.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    private String exchange = "user-exchange";//exchange sabittir
    //Add Employee Producer
    @Bean
    DirectExchange directExchange(){
        return new DirectExchange(exchange);
    }

    //Bunlar değişir
    private String addEmployeeQueue = "add-employee-queue";
    private String addEmployeeBinding = "add-employee-binding";

    @Bean
    Queue addEmployeeQueue(){
        return new Queue(addEmployeeQueue);
    }
    @Bean
    public Binding addEmployeeBinding(final Queue addEmployeeQueue, final DirectExchange directExchange){
        return BindingBuilder.bind(addEmployeeQueue).to(directExchange).with(addEmployeeBinding);
    }


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



    // auth forgot password consumer
    String userForgotPasswordQueue = "user-forgot-password-queue";

    @Bean
    Queue userForgotPasswordQueue() {
        return new Queue(userForgotPasswordQueue);
    }




}
