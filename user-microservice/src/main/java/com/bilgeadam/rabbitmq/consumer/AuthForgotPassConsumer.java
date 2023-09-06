package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.UserForgotPassModel;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthForgotPassConsumer {
    private final UserService userService;

    @RabbitListener(queues = "forgot-pass-queue")
    public void forgotPassword(UserForgotPassModel userForgotPassModel){
        userService.forgotPassword(userForgotPassModel);
    }
}
