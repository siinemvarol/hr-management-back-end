package com.bilgeadam.rabbitmq.producer;


import com.bilgeadam.rabbitmq.model.UserRegisterModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegisterProducer {
    private String exchange = "auth-exchange";

    private String userRegisterBinding = "user-register-binding";

    private final RabbitTemplate rabbitTemplate;
    public void sendRegisterProducer(UserRegisterModel userRegisterModel){
        rabbitTemplate.convertAndSend(exchange, userRegisterBinding, userRegisterModel);
    }
}
