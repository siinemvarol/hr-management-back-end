package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.GetTotalCommentsByEmployeeModel;
import com.bilgeadam.rabbitmq.model.GetTotalCommentsByEmployeeResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTotalCommentsByEmployeeProducer {
    private String exchange = "comment-exchange";
    private final String getTotalCommentsByEmployeeBinding = "get-total-comments-by-employee-binding";
    private final RabbitTemplate rabbitTemplate;
    public GetTotalCommentsByEmployeeResponseModel companyUserIdForEmployeeComments(GetTotalCommentsByEmployeeModel model){
        return (GetTotalCommentsByEmployeeResponseModel) rabbitTemplate.convertSendAndReceive(exchange, getTotalCommentsByEmployeeBinding, model);
    }
}
