package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.UserCompanyRegisterModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCompanyRegisterProducer {
    private  final RabbitTemplate rabbitTemplate;
    public void sendRegisterMessage(UserCompanyRegisterModel userRegisterModel){
        rabbitTemplate.convertAndSend("company-exchange","user-company-register-binding",userRegisterModel);
    }
}
