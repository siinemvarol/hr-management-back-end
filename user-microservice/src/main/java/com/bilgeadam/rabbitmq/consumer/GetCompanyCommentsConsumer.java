package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.GetCompanyCommentsModel;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCompanyCommentsConsumer {
    private final UserService userService;
    @RabbitListener(queues = "get-company-comments-queue")
    public String returnCompanyIdForComments(GetCompanyCommentsModel getCompanyCommentsModel){
        return userService.returnCompanyIdForComments(getCompanyCommentsModel);
    }
}
