package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.mapper.ICompanyMapper;
import com.bilgeadam.rabbitmq.model.AddCommentGetUserAndCompanyModel;
import com.bilgeadam.rabbitmq.model.AddCommentUserAndCompanyResponseModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddCommentGetUserAndCompanyProducer {
    private String exchange = "company-exchange";
    private final String addCommentGetUserAndCompanyBinding = "add-comment-get-user-and-company-binding";
    private final RabbitTemplate rabbitTemplate;
    public AddCommentUserAndCompanyResponseModel getUserIdAndCompanyId(AddCommentGetUserAndCompanyModel addCommentGetUserAndCompanyModel){
        return (AddCommentUserAndCompanyResponseModel) rabbitTemplate.convertSendAndReceive(exchange, addCommentGetUserAndCompanyBinding, addCommentGetUserAndCompanyModel);

    }

}
