package com.bilgeadam.rabbitmq.consumer;

import com.bilgeadam.mapper.IUserMapper;
import com.bilgeadam.rabbitmq.model.RegisterCompanyManagerModel;
import com.bilgeadam.rabbitmq.model.UserCreateEmployeeModel;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterCompanyManagerConsumer {
    private final UserService userService;
    @RabbitListener(queues = "register-company-manager-queue")
    public void registerCompanyManagerModel(RegisterCompanyManagerModel registerCompanyManagerModel){
        try{
            UserCreateEmployeeModel userCreateEmployeeModel = IUserMapper.INSTANCE.userCreateEmployeeModelFromModel(registerCompanyManagerModel);
            userCreateEmployeeModel.setRole(ERole.COMPANY_MANAGER);
            userService.createEmployee(userCreateEmployeeModel);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }



    }
}
