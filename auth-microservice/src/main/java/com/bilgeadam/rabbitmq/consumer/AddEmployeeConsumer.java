package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.UserCreateEmployeeModel;
import com.bilgeadam.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddEmployeeConsumer {
    private final AuthService authService;
//    @RabbitListener(queues =  "add-employee-queue")
//    public void addEmployee(UserCreateEmployeeModel userAddEmployeeModel){
//        authService.createEmployee(userAddEmployeeModel);
//    }

}
