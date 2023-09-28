package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.GetTotalCommentsByCompanyModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTotalCommentsByCompanyProducer {
    private String exchange = "comment-exchange";
    private final String getTotalCommentsByCompanyBinding = "get-total-comments-by-company-binding";
    private final RabbitTemplate rabbitTemplate;
    public String companyIdForCompanyComments(GetTotalCommentsByCompanyModel model){
        return rabbitTemplate.convertSendAndReceive(exchange, getTotalCommentsByCompanyBinding, model).toString();
    }
}
