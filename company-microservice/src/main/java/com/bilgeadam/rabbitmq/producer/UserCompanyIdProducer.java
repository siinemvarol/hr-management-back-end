package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.UserCompanyIdModel;
import com.bilgeadam.rabbitmq.model.UserCompanyRegisterModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCompanyIdProducer {

    private  final RabbitTemplate rabbitTemplate;
    public void sendCompanyIdMessage(UserCompanyIdModel userCompanyIdModel){
        rabbitTemplate.convertAndSend("company-exchange","user-company-id-binding",userCompanyIdModel);
    }

}
