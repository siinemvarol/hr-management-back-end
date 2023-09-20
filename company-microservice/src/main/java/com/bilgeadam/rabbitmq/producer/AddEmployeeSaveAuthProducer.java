package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.AddEmployeeSaveAuthModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddEmployeeSaveAuthProducer {
    private String exchange = "company-exchange";
    private final String addEmployeeSaveAuthBinding = "add-employee-save-auth-binding";
    private final RabbitTemplate rabbitTemplate;
    public Long toAuthSaveEmployee(AddEmployeeSaveAuthModel addEmployeeSaveAuthModel){
        return (Long) rabbitTemplate.convertSendAndReceive(exchange, addEmployeeSaveAuthBinding, addEmployeeSaveAuthModel);
    }
}
