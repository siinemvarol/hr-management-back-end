package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.GetPendingCommentsCompanyNameModel;
import com.bilgeadam.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetPendingCommentsCompanyNameConsumer {
    private final CompanyService companyService;
    @RabbitListener(queues = "get-pending-comments-company-name-queue")
    public String returnCompanyName(GetPendingCommentsCompanyNameModel getPendingCommentsCompanyNameModel){
        return companyService.returnCompanyName(getPendingCommentsCompanyNameModel);
    }

}
