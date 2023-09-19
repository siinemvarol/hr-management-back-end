package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.AddCommentGetUserAndCompanyModel;
import com.bilgeadam.rabbitmq.model.AddCommentUserAndCompanyResponseModel;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddCommentGetUserAndCompanyConsumer {
    private final UserService userService;
    @RabbitListener(queues = "add-comment-get-user-and-company-queue")
    public AddCommentUserAndCompanyResponseModel getUserIdAndCompanyId(AddCommentGetUserAndCompanyModel addCommentGetUserAndCompanyModel){
        return userService.getUserIdAndCompanyId(addCommentGetUserAndCompanyModel);
    }
}
