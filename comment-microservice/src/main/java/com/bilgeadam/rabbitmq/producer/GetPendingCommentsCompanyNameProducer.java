package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.GetPendingCommentsCompanyNameModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetPendingCommentsCompanyNameProducer {
    private String exchange = "comment-exchange";
    private final String getPendingCommentsCompanyNameBinding = "get-pending-comments-company-name-binding";
    private final RabbitTemplate rabbitTemplate;
    public String returnCompanyName(GetPendingCommentsCompanyNameModel getPendingCommentsCompanyNameModel){
        return rabbitTemplate.convertSendAndReceive(exchange, getPendingCommentsCompanyNameBinding, getPendingCommentsCompanyNameModel).toString();
    }

}
