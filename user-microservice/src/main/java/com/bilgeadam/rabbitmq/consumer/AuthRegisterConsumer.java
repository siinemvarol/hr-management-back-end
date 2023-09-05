package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.UserRegisterModel;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthRegisterConsumer {
    private final UserService userService;
    @RabbitListener(queues = ("user-register-queue"))
    public void consumerRegister(UserRegisterModel model){
        userService.createUser(model);
    }
}
