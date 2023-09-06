package com.bilgeadam.service;

import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.UserManagerException;
import com.bilgeadam.mapper.IUserMapper;
import com.bilgeadam.rabbitmq.model.UserCompanyRegisterModel;
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
    public UserService(IUserRepository userRepository){
        super(userRepository);
        this.userRepository = userRepository;
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
}
