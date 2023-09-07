package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.UserCompanyIdModel;
import com.bilgeadam.rabbitmq.model.UserRegisterModel;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyIdUserConsumer {

    private final UserService userService;
    @RabbitListener(queues = "user-company-id-queue")
    public void consumerCompanyId(UserCompanyIdModel model){
        userService.findByCompanyId(model);
    }
}

