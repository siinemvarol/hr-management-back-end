package com.bilgeadam.service;

import com.bilgeadam.dto.request.AuthRegisterRequestDto;
import com.bilgeadam.dto.response.AuthRegisterResponseDto;
import com.bilgeadam.exception.AuthManagerException;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.mapper.IAuthMapper;
import com.bilgeadam.rabbitmq.model.MailRegisterModel;
import com.bilgeadam.rabbitmq.producer.UserRegisterProducer;
import com.bilgeadam.repository.IAuthRepository;
import com.bilgeadam.repository.entity.Auth;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class AuthService extends ServiceManager<Auth,Long> {
    private final IAuthRepository authRepository;
    private final UserRegisterProducer userRegisterProducer;
    public AuthService(IAuthRepository authRepository,UserRegisterProducer userRegisterProducer) {
        super(authRepository);
        this.authRepository = authRepository;
        this.userRegisterProducer = userRegisterProducer;
    }
    public AuthRegisterResponseDto registerSave(AuthRegisterRequestDto dto) {
        Auth auth = IAuthMapper.INSTANCE.fromRegisterDto(dto);
        if (auth.getPassword().equals(dto.getRePassword())) {
            save(auth);
            userRegisterProducer.sendRegisterProducer(IAuthMapper.INSTANCE.fromAuthToUserRegisterModel(auth));
        } else {
            throw new AuthManagerException(ErrorType.PASSWORDS_NOT_MATCH);
        }
        AuthRegisterResponseDto authRegisterResponseDto = IAuthMapper.INSTANCE.fromAuth(auth);
        return authRegisterResponseDto;
    }
}
