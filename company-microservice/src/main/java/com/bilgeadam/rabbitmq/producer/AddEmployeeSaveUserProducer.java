package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.AddEmployeeSaveUserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddEmployeeSaveUserProducer {
    private String exchange = "company-exchange";
    private final String addEmployeeSaveUserBinding = "add-employee-save-user-binding";
    private final RabbitTemplate rabbitTemplate;
    public void toUserSaveEmployee(AddEmployeeSaveUserModel addEmployeeSaveUserModel){
        rabbitTemplate.convertAndSend(exchange, addEmployeeSaveUserBinding, addEmployeeSaveUserModel);
    }

}
