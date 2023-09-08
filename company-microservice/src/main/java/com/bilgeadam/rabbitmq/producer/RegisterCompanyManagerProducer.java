package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.RegisterCompanyManagerModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterCompanyManagerProducer {
    private  final RabbitTemplate rabbitTemplate;
    public void sendRegisterCompanyManagerModel(RegisterCompanyManagerModel registerCompanyManagerModel){
        rabbitTemplate.convertAndSend("company-exchange","register-company-manager-binding",registerCompanyManagerModel);
        System.out.println("Producerdayız"+registerCompanyManagerModel);
    }
}
