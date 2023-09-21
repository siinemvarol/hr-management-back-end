package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.CompanyRegisterModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyRegisterProducer {
    private String exchange = "auth-exchange";

    private String companyRegisterBinding = "company-register-binding";
    private final RabbitTemplate rabbitTemplate;
    public String createNewCompany(CompanyRegisterModel companyRegisterModel){
        return rabbitTemplate.convertSendAndReceive(exchange, companyRegisterBinding, companyRegisterModel).toString();
    }
}
