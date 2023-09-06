package com.bilgeadam.service;

import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.UserManagerException;
import com.bilgeadam.mapper.IUserMapper;
import com.bilgeadam.rabbitmq.model.UserAddEmployeeModel;
import com.bilgeadam.rabbitmq.model.UserCompanyRegisterModel;
import com.bilgeadam.rabbitmq.producer.AddEmloyeeProducer;
import lombok.RequiredArgsConstructor;
import com.bilgeadam.rabbitmq.model.UserForgotPassModel;
import com.bilgeadam.rabbitmq.model.UserRegisterModel;
import com.bilgeadam.repository.IUserRepository;
import com.bilgeadam.repository.entity.User;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends ServiceManager<User, String> {
    private final IUserRepository userRepository;
    private final AddEmloyeeProducer addEmloyeeProducer;
    public UserService(IUserRepository userRepository,AddEmloyeeProducer addEmloyeeProducer){
        super(userRepository);
        this.userRepository = userRepository;
        this.addEmloyeeProducer = addEmloyeeProducer;
    }

    public Boolean createUser(UserRegisterModel model) {
        System.out.println(model);
        User user = userRepository.save(IUserMapper.INSTANCE.fromRegisterModelToUserProfile(model));
        return true;
    }
    public Boolean saveCompanyUser(UserCompanyRegisterModel model) {
        userRepository.save(IUserMapper.INSTANCE.fromUserCompanyRegisterModelToUser(model));
        return true;
    }
    public Boolean forgotPassword(UserForgotPassModel userForgotPassModel){
        Optional<User> optionalUser = userRepository.findOptionalByAuthid(userForgotPassModel.getAuthid());
        if(optionalUser.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        optionalUser.get().setPassword(userForgotPassModel.getPassword());
        update(optionalUser.get());
        return true;
    }
    public UserAddEmployeeModel addEmployee(UserAddEmployeeModel userAddEmployeeModel) {
        if (userRepository.findOptionalByUsername(userAddEmployeeModel.getUsername()).isPresent()) {
            throw new UserManagerException(ErrorType.USERNAME_DUPLICATE);
        }
        addEmloyeeProducer.sendAddEmployee(userAddEmployeeModel);
        return userAddEmployeeModel;
    }
}
