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
    // add employee save mail producer (to user service)
    private final String addEmployeeMailQueue = "add-employee-mail-queue";

    @Bean
    Queue addEmployeeMailQueue() {
        return new Queue(addEmployeeMailQueue);
    }

    private final String addEmployeeMailBinding = "add-employee-mail-binding";

    @Bean
    public Binding addEmployeeMailBinding(final DirectExchange companyExchange, final Queue addEmployeeMailQueue) {
        return BindingBuilder.bind(addEmployeeMailQueue).to(companyExchange).with(addEmployeeMailBinding);
    }


    //Mail register producer
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

    //mail forgot password
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

    //User forgot password
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
    public Binding companyRegisterBinding(final DirectExchange authExchange, final Queue companyRegisterQueue){
        return BindingBuilder.bind(companyRegisterQueue).to(authExchange).with(companyRegisterBinding);
    }

    // company manager register producer: saving company manager to user when registering new company
    private final String companyManagerRegisterQueue = "company-manager-register-queue";
    @Bean
    Queue companyManagerRegisterQueue(){
        return new Queue(companyManagerRegisterQueue);
    }
    private final String companyManagerRegisterBinding = "company-manager-register-binding";
    @Bean
    public Binding companyManagerRegisterBinding(final DirectExchange authExchange, final Queue companyManagerRegisterQueue){
        return BindingBuilder.bind(companyManagerRegisterQueue).to(authExchange).with(companyManagerRegisterBinding);
    }

    // guest register producer: saving guest to user microservice when registering
    private final String guestRegisterQueue = "guest-register-queue";
    @Bean
    Queue guestRegisterQueue(){
        return new Queue(guestRegisterQueue);
    }
    private final String guestRegisterBinding = "guest-register-binding";
    @Bean
    public Binding guestRegisterBinding(final DirectExchange authExchange, final Queue guestRegisterQueue){
        return BindingBuilder.bind(guestRegisterQueue).to(authExchange).with(guestRegisterBinding);
    }

    // guest mail register producer
    private final String guestMailRegisterQueue = "guest-mail-register-queue";
    @Bean
    Queue guestMailRegisterQueue(){
        return new Queue(guestMailRegisterQueue);
    }
    private final String guestMailRegisterBinding = "guest-mail-register-binding";
    @Bean
    public Binding guestMailRegisterBinding(final DirectExchange authExchange, final Queue guestMailRegisterQueue){
        return BindingBuilder.bind(guestMailRegisterQueue).to(authExchange).with(guestMailRegisterBinding);
    }

    // add employee save auth consumer (from company service)
    private final String addEmployeeSaveAuthQueue = "add-employee-save-auth-queue";
    @Bean
    Queue addEmployeeSaveAuthQueue(){
        return new Queue(addEmployeeSaveAuthQueue);
    }

}