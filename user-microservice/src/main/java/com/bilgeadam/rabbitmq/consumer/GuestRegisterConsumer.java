package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.GuestRegisterModel;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestRegisterConsumer {
    private final UserService userService;
    @RabbitListener(queues = "guest-register-queue")
    public void createNewGuest(GuestRegisterModel guestRegisterModel){
        userService.createNewGuest(guestRegisterModel);
    }
}
