package com.bilgeadam.rabbitmq.producer;

import com.bilgeadam.rabbitmq.model.AddCommentSaveCommentModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddCommentSaveCommentProducer {
    private String exchange = "company-exchange";
    private final String addCommentSaveCommentBinding = "add-comment-save-comment-binding";
    private final RabbitTemplate rabbitTemplate;
    public void sendCommentToSave(AddCommentSaveCommentModel addCommentSaveCommentModel){
        rabbitTemplate.convertAndSend(exchange, addCommentSaveCommentBinding, addCommentSaveCommentModel);
    }
}
