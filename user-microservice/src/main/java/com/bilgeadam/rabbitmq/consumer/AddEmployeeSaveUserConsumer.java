package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.AddEmployeeSaveUserModel;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddEmployeeSaveUserConsumer {
    private final UserService userService;
    @RabbitListener(queues = "add-employee-save-user-queue")
    public void toUserSaveEmployee(AddEmployeeSaveUserModel addEmployeeSaveUserModel){
        userService.addEmployeeSaveUser(addEmployeeSaveUserModel);
    }
}
