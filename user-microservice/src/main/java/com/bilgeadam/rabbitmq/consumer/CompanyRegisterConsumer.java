package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.UserCompanyRegisterModel;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyRegisterConsumer {
    private final UserService userService;
    @RabbitListener(queues = "user-company-register-queue")
    public void consumerRegister(UserCompanyRegisterModel model) {
        userService.saveCompanyUser(model);
    }
}
