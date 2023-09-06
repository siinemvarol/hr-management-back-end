package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.AuthRegisterModel;

import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthRegisterConsumer {
    private final UserService userService;
    @RabbitListener(queues = "user-register-queue")
    public void consumerRegister(AuthRegisterModel model){
        userService.createUser(model);
    }
}
