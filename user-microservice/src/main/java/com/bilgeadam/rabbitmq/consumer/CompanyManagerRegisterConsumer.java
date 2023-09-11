package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.CompanyManagerRegisterModel;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyManagerRegisterConsumer {

    private final UserService userService;

    @RabbitListener(queues = "company-manager-register-queue")
    public void createNewCompanyManager(CompanyManagerRegisterModel companyManagerRegisterModel){
        userService.createNewCompanyManager(companyManagerRegisterModel);
    }
}
