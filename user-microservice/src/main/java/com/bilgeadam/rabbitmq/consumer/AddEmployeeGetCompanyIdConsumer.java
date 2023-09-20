package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.AddEmployeeGetCompanyIdModel;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddEmployeeGetCompanyIdConsumer {
    private final UserService userService;
    @RabbitListener(queues = "add-employee-get-company-id-queue")
    public String toUserGetCompanyId(AddEmployeeGetCompanyIdModel addEmployeeGetCompanyNameModel){
        return userService.returnCompanyIdForEmployee(addEmployeeGetCompanyNameModel);
    }
}
