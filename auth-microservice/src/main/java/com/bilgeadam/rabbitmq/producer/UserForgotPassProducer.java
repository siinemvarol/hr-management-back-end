package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.UserForgotPassModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserForgotPassProducer {

    private String exchange = "auth-exchange";
    private String userForgotPasswordBinding = "user-forgot-user-binding";

    private final RabbitTemplate rabbitTemplate;

    public void userForgotPassword(UserForgotPassModel userForgotPassModel) {

        rabbitTemplate.convertAndSend(exchange, userForgotPasswordBinding, userForgotPassModel);
        System.out.println("userForgotPassProducer : " + userForgotPassModel);
    }
}
