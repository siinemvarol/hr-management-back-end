package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.CompanyManagerRegisterModel;
import com.bilgeadam.rabbitmq.model.CompanyRegisterModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyManagerRegisterProducer {

    private String exchange = "auth-exchange";

    private String companyManagerRegisterBinding = "company-manager-register-binding";
    private final RabbitTemplate rabbitTemplate;
    public void sendCompanyManager(CompanyManagerRegisterModel companyManagerRegisterModel){
        rabbitTemplate.convertAndSend(exchange, companyManagerRegisterBinding, companyManagerRegisterModel);
    }
}
