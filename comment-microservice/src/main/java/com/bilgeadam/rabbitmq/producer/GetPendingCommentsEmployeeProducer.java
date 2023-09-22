package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.GetPendingCommentsEmployeeModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetPendingCommentsEmployeeProducer {
    private String exchange = "comment-exchange";
    private final String getPendingCommentsEmployeeBinding = "get-pending-comments-employee-binding";
    private final RabbitTemplate rabbitTemplate;
    public String returnEmployeeNameSurname(GetPendingCommentsEmployeeModel getPendingCommentsEmployeeModel){
        return rabbitTemplate.convertSendAndReceive(exchange, getPendingCommentsEmployeeBinding, getPendingCommentsEmployeeModel).toString();
    }
}
