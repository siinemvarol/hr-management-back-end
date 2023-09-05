package com.bilgeadam.service;

import com.bilgeadam.mapper.IUserMapper;
import com.bilgeadam.rabbitmq.model.UserCompanyRegisterModel;
import com.bilgeadam.rabbitmq.model.UserRegisterModel;
import com.bilgeadam.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IUserRepository userRepository;

    public Boolean createUser(UserRegisterModel model) {
        userRepository.save(IUserMapper.INSTANCE.fromRegisterModelToUserProfile(model));
        return true;
    }
    public Boolean saveCompanyUser(UserCompanyRegisterModel model) {
        userRepository.save(IUserMapper.INSTANCE.fromUserCompanyRegisterModelToUser(model));
        return true;
    }
}
