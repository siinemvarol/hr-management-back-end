package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.rabbitmq.model.GetCompanyEmployeesCompanyIdModel;
import com.bilgeadam.rabbitmq.model.GetCompanyEmployeesResponseModel;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCompanyEmployeesCompanyIdConsumer {
    private final UserService userService;
    @RabbitListener(queues = "get-company-employees-company-id-queue")
    public List<GetCompanyEmployeesResponseModel> sendAuthIdGetEmployees(GetCompanyEmployeesCompanyIdModel model){
        return userService.sendAuthIdGetEmployees(model);
    }
}
