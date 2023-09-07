package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.AddEmployeeCompanyModel;
import com.bilgeadam.rabbitmq.model.UserRegisterModel;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddEmployeeCompanyConsumer {
    private final UserService userService;

    @RabbitListener(queues = "add-employee-company-queue")
    public void addEmployeeCompany(AddEmployeeCompanyModel model){
        userService.addEmployeeCompany(model);
    }
}
