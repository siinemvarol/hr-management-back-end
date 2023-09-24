package com.bilgeadam.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    private String exchange = "comment-exchange";
    @Bean
    DirectExchange commentExchange(){
        return new DirectExchange(exchange);
    }

    // get company comments producer (to user service to get companyId)
    private final String getCompanyCommentsQueue="get-company-comments-queue";
    @Bean
    Queue getCompanyCommentsQueue(){
        return new Queue(getCompanyCommentsQueue);
    }
    private final String getCompanyCommentsBinding = "get-company-comments-binding";
    @Bean
    public Binding getCompanyCommentsBinding(final DirectExchange commentExchange, final Queue getCompanyCommentsQueue){
        return BindingBuilder.bind(getCompanyCommentsQueue).to(commentExchange).with(getCompanyCommentsBinding);
    }

    // get pending comments get company name producer (to company service to get companyName)
    private final String getPendingCommentsCompanyNameQueue = "get-pending-comments-company-name-queue";
    @Bean
    Queue getPendingCommentsCompanyNameQueue(){
        return new Queue(getPendingCommentsCompanyNameQueue);
    }
    private final String getPendingCommentsCompanyNameBinding = "get-pending-comments-company-name-binding";
    @Bean
    public Binding getPendingCommentsCompanyNameBinding(final DirectExchange commentExchange, final Queue getPendingCommentsCompanyNameQueue){
        return BindingBuilder.bind(getPendingCommentsCompanyNameQueue).to(commentExchange).with(getPendingCommentsCompanyNameBinding);
    }

    // get pending comments get employee name and surname producer (to user service to get name and surname)
    private final String getPendingCommentsEmployeeQueue = "get-pending-comments-employee-queue";
    @Bean
    Queue getPendingCommentsEmployeeQueue(){
        return new Queue(getPendingCommentsEmployeeQueue);
    }
    private final String getPendingCommentsEmployeeBinding = "get-pending-comments-employee-binding";
    @Bean
    public Binding getPendingCommentsEmployeeBinding(final DirectExchange commentExchange, final Queue getPendingCommentsEmployeeQueue){
        return BindingBuilder.bind(getPendingCommentsEmployeeQueue).to(commentExchange).with(getPendingCommentsEmployeeBinding);
    }

    // add comment save comment consumer
    private final String addCommentSaveCommentQueue = "add-comment-save-comment-queue";
    @Bean
    Queue addCommentSaveCommentQueue(){
        return new Queue(addCommentSaveCommentQueue);
    }
}
