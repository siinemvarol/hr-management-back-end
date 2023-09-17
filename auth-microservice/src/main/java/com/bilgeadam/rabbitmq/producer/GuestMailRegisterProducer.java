package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.CompanyRegisterModel;
import com.bilgeadam.rabbitmq.model.GuestMailRegisterModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestMailRegisterProducer {
    private String exchange = "auth-exchange";

    private final String guestMailRegisterBinding = "guest-mail-register-binding";
    private final RabbitTemplate rabbitTemplate;
    public void sendMailRegister(GuestMailRegisterModel guestMailRegisterModel){
        System.out.println("company register model producer: " + guestMailRegisterModel);
        rabbitTemplate.convertAndSend(exchange, guestMailRegisterBinding, guestMailRegisterModel);
    }
}
