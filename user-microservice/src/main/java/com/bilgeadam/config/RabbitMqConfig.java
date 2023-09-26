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

    // get company information consumer (from company service)
    private final String getCompanyInformationQueue = "get-company-information-queue";
    @Bean
    Queue getCompanyInformationQueue(){
        return new Queue(getCompanyInformationQueue);
    }

    // get company comments consumer (from comment service)
    private final String getCompanyCommentsQueue="get-company-comments-queue";
    @Bean
    Queue getCompanyCommentsQueue(){
        return new Queue(getCompanyCommentsQueue);
    }

    // add comment get user and company consumer (from company service)
    private final String addCommentGetUserAndCompanyQueue = "add-comment-get-user-and-company-queue";
    @Bean
    Queue addCompanyGetUserAndCompanyQueue(){
        return new Queue(addCommentGetUserAndCompanyQueue);
    }

    // add employee get company id consumer (from company service)
    private final String addEmployeeGetCompanyIdQueue = "add-employee-get-company-id-queue";
    @Bean
    Queue addEmployeeGetCompanyIdQueue(){
        return new Queue(addEmployeeGetCompanyIdQueue);
    }

    // add employee save user consumer (from company service)
    private final String addEmployeeSaveUserQueue = "add-employee-save-user-queue";
    @Bean
    Queue addEmployeeSaveUserQueue(){
        return new Queue(addEmployeeSaveUserQueue);
    }

    // get pending comments employee consumer (from comment service)
    private final String getPendingCommentsEmployeeQueue = "get-pending-comments-employee-queue";
    @Bean
    Queue getPendingCommentsEmployeeQueue(){
        return new Queue(getPendingCommentsEmployeeQueue);
    }

    // get company information manager consumer (from company service)
    private final String getCompanyInformationManagerQueue = "get-company-information-manager-queue";

    @Bean
    Queue getCompanyInformationManagerQueue() {
        return new Queue(getCompanyInformationManagerQueue);
    }

    // get company valuation manager consumer (from company service)
    private final String getCompanyValuationManagerQueue = "get-company-valuation-manager-queue";
    @Bean
    Queue getCompanyValuationManagerQueue(){
        return new Queue(getCompanyValuationManagerQueue);
    }

    // update company information consumer (company information page) (from company service)
    private final String updateCompanyInformationQueue = "update-company-information-queue";
    @Bean
    Queue updateCompanyInformationQueue(){
        return new Queue(updateCompanyInformationQueue);
    }

    // update company valuation consumer (company information page) (from company service)
    private final String updateCompanyValuationQueue = "update-company-valuation-queue";
    @Bean
    Queue updateCompanyValuationQueue(){
        return new Queue(updateCompanyValuationQueue);
    }

    // get company employees for company manager consumer (from company service)
    private final String getCompanyEmployeesCompanyIdQueue = "get-company-employees-company-id-queue";
    @Bean
    Queue getCompanyEmployeesCompanyIdQueue() {
        return new Queue(getCompanyEmployeesCompanyIdQueue);
    }

}
