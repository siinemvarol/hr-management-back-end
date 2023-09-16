package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.UserForgotPassModel;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class UserForgotPassConsumer {
    private final UserService userService;
    @RabbitListener(queues = "user-forgot-password-queue")
    public void userForgotConsumer(UserForgotPassModel userForgotPassModel){
        userService.forgotPassword(userForgotPassModel);
        System.out.println("userForgotConsumer : " + userForgotPassModel);
    }
}