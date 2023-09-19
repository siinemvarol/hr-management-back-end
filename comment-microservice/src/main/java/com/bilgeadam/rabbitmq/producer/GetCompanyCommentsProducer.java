package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.GetCompanyCommentsModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCompanyCommentsProducer {
    private String exchange = "comment-exchange";
    private final String getCompanyCommentsBinding = "get-company-comments-binding";
    private final RabbitTemplate rabbitTemplate;
    public String sendAuthIdToUser(GetCompanyCommentsModel getCompanyCommentsModel){
        return rabbitTemplate.convertSendAndReceive(exchange, getCompanyCommentsBinding, getCompanyCommentsModel).toString();
    }

}
