package com.bilgeadam.service;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.AuthRegisterResponseDto;
import com.bilgeadam.exception.AuthManagerException;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.mapper.IAuthMapper;
import com.bilgeadam.rabbitmq.model.*;
import com.bilgeadam.rabbitmq.producer.*;
import com.bilgeadam.repository.IAuthRepository;
import com.bilgeadam.repository.entity.Auth;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.utility.CodeGenerator;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService extends ServiceManager<Auth, Long> {
    private final IAuthRepository authRepository;
    private final UserRegisterProducer userRegisterProducer;
    private final MailRegisterProducer mailRegisterProducer;
    private  final UserForgotPassProducer userForgotPassProducer;
    private final MailForgotPasswordProducer mailForgotPassProducer;
    private final CompanyRegisterProducer companyRegisterProducer;
    private final CompanyManagerRegisterProducer companyManagerRegisterProducer;

    public AuthService(IAuthRepository authRepository, UserRegisterProducer userRegisterProducer,
                       MailForgotPasswordProducer mailForgotPassProducer, UserForgotPassProducer userForgotPassProducer,
                       MailRegisterProducer mailRegisterProducer, CompanyRegisterProducer companyRegisterProducer,
                       CompanyManagerRegisterProducer companyManagerRegisterProducer) {
        super(authRepository);
        this.authRepository = authRepository;
        this.userRegisterProducer = userRegisterProducer;
        this.userForgotPassProducer = userForgotPassProducer;
        this.mailRegisterProducer = mailRegisterProducer;
        this.mailForgotPassProducer = mailForgotPassProducer;
        this.companyRegisterProducer = companyRegisterProducer;
        this.companyManagerRegisterProducer = companyManagerRegisterProducer;
    }

    public void createEmployee(UserCreateEmployeeModel model) {

        Auth auth = IAuthMapper.INSTANCE.authFromUserAddEmployeeModel(model);
        auth.setActivationLink(CodeGenerator.generateCode());
        if (authRepository.findByUsername(auth.getUsername()).isPresent()){
            throw new AuthManagerException(ErrorType.BAD_REQUEST);
        }
        auth = authRepository.save(auth);
        UserRegisterModel userRegisterModel = IAuthMapper.INSTANCE.fromAuthToUserRegisterModel(auth);
        userRegisterModel.setCompanyId(model.getCompanyId());
        userRegisterModel.setName(model.getName());
        userRegisterProducer.sendRegisterProducer(userRegisterModel);
        MailRegisterModel mailRegisterModel = IAuthMapper.INSTANCE.fromAuthToMailRegisterModel(auth);
        mailRegisterModel.setActivationLink(auth.getId() + "-" + auth.getActivationLink());
        mailRegisterProducer.sendMailRegister(mailRegisterModel);
    }

    public Boolean login(AuthLoginRequestDto dto) {

        Optional<Auth> optionalAuth = authRepository.findOptionalByCompanyEmailAndPassword(dto.getEmail(), dto.getPassword());

        if (optionalAuth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (!optionalAuth.get().getEStatus().equals(EStatus.ACTIVE)) {
            throw new AuthManagerException(ErrorType.ACCOUNT_NOT_ACTIVE);
        }
        return true;
    }

    public String forgotPassword(AuthForgotPasswordRequestDto dto) {
        Optional<Auth> optionalAuth = authRepository.findOptionalByCompanyEmail(dto.getEmail());
        if (optionalAuth.isPresent() && optionalAuth.get().getEStatus().equals(EStatus.ACTIVE)) {
            //random password
            String randomPassword = UUID.randomUUID().toString();
            optionalAuth.get().setPassword(randomPassword);
            update(optionalAuth.get());
            UserForgotPassModel userForgotPassModel = UserForgotPassModel.builder().password(randomPassword).authid(optionalAuth.get().getId()).build();
            userForgotPassProducer.userForgotPassword(userForgotPassModel);
            mailForgotPassProducer.forgotPasswordSendMail(IAuthMapper.INSTANCE.fromAuthToMailForgotPassModel(optionalAuth.get()));

            return "New password is:" + optionalAuth.get().getPassword();
        }
        throw new AuthManagerException(ErrorType.ACCOUNT_NOT_ACTIVE);
    }


    public RegisterRequestDto registerSave(RegisterRequestDto registerRequestDto) {


        return null;
    }
    public Auth userActive(String token) {
        Long authid = Long.parseLong(token.split("-")[0]);
        String activationLink = token.split("-")[1];
        Optional<Auth> optionalAuth = authRepository.findOptionalById(authid);
        if (optionalAuth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (optionalAuth.get().getEStatus().equals(EStatus.ACTIVE)) {
            throw new AuthManagerException(ErrorType.ACCOUNT_ALREADY_ACTIVE);
        }
        if (optionalAuth.get().getActivationLink().equals(activationLink)) {
            optionalAuth.get().setEStatus(EStatus.ACTIVE);
            update(optionalAuth.get());
            UserRegisterModel userRegisterModel = IAuthMapper.INSTANCE.fromAuthToUserRegisterModel(optionalAuth.get());
            userRegisterModel.setStatus(EStatus.ACTIVE);
            userRegisterProducer.sendRegisterProducer(userRegisterModel);
        } else throw new AuthManagerException(ErrorType.ACCOUNT_NOT_ACTIVE);
        return optionalAuth.get();

    }

    public Boolean companyRegister(CompanyRegisterRequestDto dto){
        Auth auth = IAuthMapper.INSTANCE.fromCompanyRegisterRequestDtoToAuth(dto);
        save(auth);
        CompanyRegisterModel companyRegisterModel = IAuthMapper.INSTANCE.fromCompanyRegisterRequestDtoToCompanyRegisterModel(dto);
        companyRegisterProducer.sendCompany(companyRegisterModel);
        companyManagerRegisterProducer.sendCompanyManager(IAuthMapper.INSTANCE.fromAuthToCompanyManagerRegisterModel(auth));
        return true;
    }
}
