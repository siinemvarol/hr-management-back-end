package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.AddEmployeeSaveAuthModel;
import com.bilgeadam.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddEmployeeSaveAuthConsumer {
    private final AuthService authService;
    @RabbitListener(queues = "add-employee-save-auth-queue")
    public Long toAuthSaveEmployee(AddEmployeeSaveAuthModel addEmployeeSaveAuthModel){
        return authService.saveEmployeeReturnId(addEmployeeSaveAuthModel);
    }
}
