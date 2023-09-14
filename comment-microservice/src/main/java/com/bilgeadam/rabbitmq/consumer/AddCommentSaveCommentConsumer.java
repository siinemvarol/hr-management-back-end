package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.AddCommentSaveCommentModel;
import com.bilgeadam.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddCommentSaveCommentConsumer {
    private final CommentService commentService;
    @RabbitListener(queues = "add-comment-save-comment-queue")
    public void addCommentSaveComment(AddCommentSaveCommentModel addCommentSaveCommentModel){
        commentService.addCommentSaveComment(addCommentSaveCommentModel);
    }
}
