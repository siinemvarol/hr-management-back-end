package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.UserAddEmployeeModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddEmloyeeProducer {
    private String exchange =  "user-exchange";
    private String addEmployeeBinding = "add-employee-binding";

    private final RabbitTemplate rabbitTemplate;
    public void sendAddEmployee( UserAddEmployeeModel userAddEmployeeModel){
        rabbitTemplate.convertAndSend(exchange, addEmployeeBinding, userAddEmployeeModel);
    }
}
