package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.GetTotalCommentsByCompanyModel;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetTotalCommentsByCompanyConsumer {
    private final UserService userService;
    @RabbitListener(queues = "get-total-comments-by-company-queue")
    public String companyIdForCompanyComments(GetTotalCommentsByCompanyModel model){
        return userService.companyIdForCompanyComments(model);
    }
}
