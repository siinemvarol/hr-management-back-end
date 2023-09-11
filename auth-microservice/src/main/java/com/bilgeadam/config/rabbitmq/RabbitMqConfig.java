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

    //Consumer
    String userAddEmployeeQueue = "add-employee-queue";
    @Bean
    Queue userAddEmployeeQueue(){
        return new Queue(userAddEmployeeQueue);
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
        return BindingBuilder.bind(userRegisterQueue).to(authExchange).with(userRegisterBinding);
    }

    //Mail register producer(Auth için activation mail gönderimi)
    private String mailRegisterQueue = "mail-register-queue";

    @Bean
    Queue mailRegisterQueue(){
        return new Queue(mailRegisterQueue);
    }
    private String mailRegisterBinding = "mail-register-binding";

    @Bean
    public Binding mailRegisterBinding(final Queue mailRegisterQueue, final DirectExchange authExchange){
        return BindingBuilder.bind(mailRegisterQueue).to(authExchange).with(mailRegisterBinding);
    }


    //maile forgot password gönderimi
    private String mailForgotPasswordQueue = "mail-forgot-password-queue";

    @Bean
    Queue mailForgotPasswordQueue(){
        return new Queue(mailForgotPasswordQueue);
    }
    private String mailForgotPassBinding = "mail-forgot-password-binding";

    @Bean
    public Binding registerMailBinding(final Queue mailForgotPasswordQueue, final DirectExchange authExchange){
        return BindingBuilder.bind(mailForgotPasswordQueue).to(authExchange).with(mailForgotPassBinding);
    }



    //Usera forgot password gönderimi
    private String userForgotPasswordQueue = "user-forgot-password-queue";

    @Bean
    Queue userForgotPasswordQueue(){
        return new Queue(userForgotPasswordQueue);
    }
    private String userForgotPasswordBinding = "user-forgot-user-binding";

    @Bean
    public Binding userForgotPasswordBinding(final Queue userForgotPasswordQueue, final DirectExchange authExchange){
        return BindingBuilder.bind(userForgotPasswordQueue).to(authExchange).with(userForgotPasswordBinding);
    }

    // company register producer: saving company when registering new company with company manager
    private final String companyRegisterQueue = "company-register-queue";
    @Bean
    Queue companyRegisterQueue(){
        return new Queue(companyRegisterQueue);
    }
    private final String companyRegisterBinding = "company-register-binding";
    @Bean
    public Binding companyRegisterMailBinding(final DirectExchange authExchange, final Queue companyRegisterQueue){
        return BindingBuilder.bind(companyRegisterQueue).to(authExchange).with(companyRegisterBinding);
    }

}