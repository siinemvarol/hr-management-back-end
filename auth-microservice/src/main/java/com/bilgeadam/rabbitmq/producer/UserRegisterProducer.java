package com.bilgeadam.rabbitmq.producer;


import com.bilgeadam.rabbitmq.model.UserRegisterModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegisterProducer {
    private final RabbitTemplate rabbitTemplate;
    public void sendRegisterMessage(UserRegisterModel userRegisterModel){
        rabbitTemplate.convertAndSend("auth-exchange",
                "user-register-binding", userRegisterModel);
    }
}
