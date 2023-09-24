package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.AddEmployeeMailModel;
import com.bilgeadam.rabbitmq.model.AddEmployeeSaveAuthModel;
import com.bilgeadam.rabbitmq.model.AddEmployeeSaveUserModel;
import lombok.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddEmployeeMailProducer {
    private String exchange = "company-exchange";
    private final String addEmployeeMailBinding = "add-employee-mail-binding";
    private final RabbitTemplate rabbitTemplate;
    public void sendMail(AddEmployeeMailModel mailModel){
        rabbitTemplate.convertAndSend(exchange, addEmployeeMailBinding, mailModel);
    }
}
