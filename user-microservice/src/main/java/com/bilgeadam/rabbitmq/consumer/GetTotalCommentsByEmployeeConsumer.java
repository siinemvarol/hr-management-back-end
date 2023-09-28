package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.GetTotalCommentsByEmployeeModel;
import com.bilgeadam.rabbitmq.model.GetTotalCommentsByEmployeeResponseModel;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTotalCommentsByEmployeeConsumer {
    private final UserService userService;
    @RabbitListener(queues = "get-total-comments-by-employee-queue")
    public GetTotalCommentsByEmployeeResponseModel companyUserIdForEmployeeComments(GetTotalCommentsByEmployeeModel model){
        return userService.companyUserIdForEmployeeComments(model);
    }
}
