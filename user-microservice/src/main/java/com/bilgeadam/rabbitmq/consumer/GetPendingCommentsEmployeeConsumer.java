package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.GetPendingCommentsEmployeeModel;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetPendingCommentsEmployeeConsumer {
    private final UserService userService;
    @RabbitListener(queues = "get-pending-comments-employee-queue")
    public String returnEmployeeNameSurname(GetPendingCommentsEmployeeModel getPendingCommentsEmployeeModel){
        return userService.returnEmployeeNameSurname(getPendingCommentsEmployeeModel);
    }
}
