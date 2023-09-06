package com.bilgeadam.service;

import com.bilgeadam.dto.request.AuthForgotPasswordRequestDto;
import com.bilgeadam.dto.request.AuthLoginRequestDto;
import com.bilgeadam.dto.request.AuthRegisterRequestDto;
import com.bilgeadam.dto.response.AuthRegisterResponseDto;
import com.bilgeadam.exception.AuthManagerException;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.mapper.IAuthMapper;
import com.bilgeadam.rabbitmq.model.MailRegisterModel;
import com.bilgeadam.rabbitmq.model.UserAddEmployeeModel;
import com.bilgeadam.rabbitmq.model.UserRegisterModel;
import com.bilgeadam.rabbitmq.producer.MailForgotPassProducer;
import com.bilgeadam.rabbitmq.producer.UserForgotPassProducer;
import com.bilgeadam.rabbitmq.producer.UserRegisterProducer;
import com.bilgeadam.repository.IAuthRepository;
import com.bilgeadam.repository.entity.Auth;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService extends ServiceManager<Auth, Long> {
    private final IAuthRepository authRepository;
    private final UserRegisterProducer userRegisterProducer;
    private final MailForgotPassProducer mailForgotPassProducer;

    private final UserForgotPassProducer userForgotPassProducer;

    public AuthService(IAuthRepository authRepository, UserRegisterProducer userRegisterProducer, MailForgotPassProducer mailForgotPassProducer, UserForgotPassProducer userForgotPassProducer) {
        super(authRepository);
        this.authRepository = authRepository;
        this.userRegisterProducer = userRegisterProducer;
        this.mailForgotPassProducer = mailForgotPassProducer;
        this.userForgotPassProducer = userForgotPassProducer;
    }

    public Auth addEmployee(UserAddEmployeeModel model){
        System.out.println(model);
        Auth auth = IAuthMapper.INSTANCE.authFromUserAddEmployeeModel(model);
        System.out.println(auth);
        authRepository.save(auth);
        UserRegisterModel userRegisterModel = IAuthMapper.INSTANCE.fromAuthToUserRegisterModel(auth);
        userRegisterProducer.sendRegisterProducer(userRegisterModel);
        return auth;
    }

    public Boolean login(AuthLoginRequestDto dto) {

        Optional<Auth> optionalAuth = authRepository.findOptionalByEmailAndPassword(dto.getEmail(), dto.getPassword());

        if (optionalAuth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (!optionalAuth.get().getEStatus().equals(EStatus.ACTIVE)) {
            throw new AuthManagerException(ErrorType.ACCOUNT_NOT_ACTIVE);
        }
        return true;
    }

    public String forgotPassword(AuthForgotPasswordRequestDto dto) {
        Optional<Auth> optionalAuth = authRepository.findOptionalByEmail(dto.getEmail());
        if (optionalAuth.isPresent() && optionalAuth.get().getEStatus().equals(EStatus.ACTIVE)) {
            //random password
            String randomPassword = UUID.randomUUID().toString();
            optionalAuth.get().setPassword(randomPassword);
            update(optionalAuth.get());
            userForgotPassProducer.userForgotPassword(IAuthMapper.INSTANCE.fromAuthToUserForgotPassModel(optionalAuth.get()));
            mailForgotPassProducer.forgotPasswordSendMail(IAuthMapper.INSTANCE.fromAuthToMailForgotPassModel(optionalAuth.get()));

            return "New password is:" + optionalAuth.get().getPassword();
        }
        throw new AuthManagerException(ErrorType.ACCOUNT_NOT_ACTIVE);
    }

}
