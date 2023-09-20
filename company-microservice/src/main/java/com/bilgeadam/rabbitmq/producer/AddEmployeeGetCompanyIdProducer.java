package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.AddEmployeeGetCompanyIdModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddEmployeeGetCompanyIdProducer {
    private String exchange = "company-exchange";
    private final String addEmployeeGetCompanyIdBinding = "add-employee-get-company-id-binding";
    private final RabbitTemplate rabbitTemplate;
    public String toUserGetCompanyId(AddEmployeeGetCompanyIdModel addEmployeeGetCompanyIdModel){
        return rabbitTemplate.convertSendAndReceive(exchange, addEmployeeGetCompanyIdBinding, addEmployeeGetCompanyIdModel).toString();
    }
}
