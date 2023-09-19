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

    //Producer
    private String userListCompanyQueue = "userList-company-queue";
    private String userListCompanyBinding = "userList-company-binding";

    @Bean
    Queue userListCompanyQueue(){
        return new Queue(userListCompanyQueue);
    }
    @Bean
    public Binding userListCompanyBinding(final Queue userListCompanyQueue, final DirectExchange directExchange){
        return BindingBuilder.bind(userListCompanyQueue).to(directExchange).with(userListCompanyBinding);
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

    //company Id user consumer
    private final String userCompanyIdQueue = "user-company-id-queue";
    @Bean
    Queue userCompanyIdQueue(){
        return new Queue(userCompanyIdQueue);
    }

    //addEmployeeCompany Consumer
    private String addEmployeeCompanyQueue = "add-employee-company-queue";
    @Bean
    Queue addEmployeeCompanyQueue(){
        return new Queue(addEmployeeCompanyQueue);
    }

    //RegisterCompanyeManager Consumer

    private String registerCompanyManagerQueue = "register-company-manager-queue";
    @Bean
    Queue registerCompanyManagerQueue(){
        return new Queue(registerCompanyManagerQueue);
    }

    // company manager register consumer
    String companyManagerRegisterQueue = "company-manager-register-queue";

    @Bean
    Queue companyManagerRegisterQueue(){
        return new Queue(companyManagerRegisterQueue);
    }

    // guest register consumer
    String guestRegisterQueue = "guest-register-queue";

    @Bean
    Queue guestRegisterQueue(){
        return new Queue(guestRegisterQueue);
    }

    // get company information consumer (from auth service)
    private final String getCompanyInformationQueue = "get-company-information-queue";
    @Bean
    Queue getCompanyInformationQueue(){
        return new Queue(getCompanyInformationQueue);
    }


}
