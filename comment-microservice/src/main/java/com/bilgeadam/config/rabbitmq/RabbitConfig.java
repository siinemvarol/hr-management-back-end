package com.bilgeadam.config.rabbitmq;

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

    // add comment save comment consumer
    private final String addCommentSaveCommentQueue = "add-comment-save-comment-queue";
    @Bean
    Queue addCommentSaveCommentQueue(){
        return new Queue(addCommentSaveCommentQueue);
    }
}
