package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.GuestRegisterModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestRegisterProducer {
    private String exchange = "auth-exchange";
    private String guestRegisterBinding = "guest-register-binding";
    private final RabbitTemplate rabbitTemplate;
    public void sendGuest(GuestRegisterModel guestRegisterModel){
        rabbitTemplate.convertAndSend(exchange, guestRegisterBinding, guestRegisterModel);
    }
}
