package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.UserCreateEmployeeModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddEmloyeeProducer {
    private String exchange =  "user-exchange";
    private String addEmployeeBinding = "add-employee-binding";

    private final RabbitTemplate rabbitTemplate;
    public void sendAddEmployee( UserCreateEmployeeModel userAddEmployeeModel){
        rabbitTemplate.convertAndSend(exchange, addEmployeeBinding, userAddEmployeeModel);
    }
}
