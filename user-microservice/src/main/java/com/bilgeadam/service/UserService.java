package com.bilgeadam.service;

import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.UserManagerException;
import com.bilgeadam.mapper.IUserMapper;
import com.bilgeadam.rabbitmq.model.*;
import com.bilgeadam.rabbitmq.producer.AddEmloyeeProducer;
import com.bilgeadam.rabbitmq.producer.UserCompanyIdModelsProducer;
import com.bilgeadam.repository.IUserRepository;
import com.bilgeadam.repository.entity.User;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService extends ServiceManager<User, String> {
    private final IUserRepository userRepository;
    private final AddEmloyeeProducer addEmloyeeProducer;
    private final UserCompanyIdModelsProducer userCompanyIdModelsProducer;
    public UserService(IUserRepository userRepository, AddEmloyeeProducer addEmloyeeProducer, UserCompanyIdModelsProducer userCompanyIdModelsProducer){
        super(userRepository);
        this.userRepository = userRepository;
        this.addEmloyeeProducer = addEmloyeeProducer;
        this.userCompanyIdModelsProducer = userCompanyIdModelsProducer;
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

    public void findByCompanyId(UserCompanyIdModel model) {
        List<UserCompanyIdModel> companyIdModels = new ArrayList<>();
        List<User> userList = userRepository.findByCompanyId(model.getCompanyId());
        System.out.println(userList + "userservice");
        userList.forEach(x -> {
            UserCompanyIdModel userCompanyIdModel = IUserMapper.INSTANCE.userCompanyIdModelFromUser(x);
            companyIdModels.add(userCompanyIdModel);
        });
        userCompanyIdModelsProducer.sendUserList(companyIdModels);
    }
}
