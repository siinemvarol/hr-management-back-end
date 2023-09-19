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

    // add comment save comment consumer
    private final String addCommentSaveCommentQueue = "add-comment-save-comment-queue";
    @Bean
    Queue addCommentSaveCommentQueue(){
        return new Queue(addCommentSaveCommentQueue);
    }
}
