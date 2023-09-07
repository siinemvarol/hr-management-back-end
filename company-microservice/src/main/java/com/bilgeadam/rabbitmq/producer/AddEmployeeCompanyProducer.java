package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.AddEmployeeCompanyModel;
import com.bilgeadam.rabbitmq.model.UserCompanyIdModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddEmployeeCompanyProducer {
    private  final RabbitTemplate rabbitTemplate;
    private String exchange =  "company-exchange";
    private String addEmployeeCompanyBinding = "add-employee-company-binding";
    public void sendAddEmployeeMessage(AddEmployeeCompanyModel addEmployeeCompanyModel){
        rabbitTemplate.convertAndSend(exchange,addEmployeeCompanyBinding,addEmployeeCompanyModel);
    }
}
