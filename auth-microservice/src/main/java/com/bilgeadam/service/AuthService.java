package com.bilgeadam.service;

import com.bilgeadam.dto.request.AuthForgotPasswordRequestDto;
import com.bilgeadam.dto.request.AuthLoginRequestDto;
import com.bilgeadam.dto.request.CompanyRegisterRequestDto;
import com.bilgeadam.dto.request.GuestRegisterRequestDto;
import com.bilgeadam.exception.AuthManagerException;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.mapper.IAuthMapper;
import com.bilgeadam.rabbitmq.model.*;
import com.bilgeadam.rabbitmq.producer.*;
import com.bilgeadam.repository.IAuthRepository;
import com.bilgeadam.repository.entity.Auth;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.utility.CodeGenerator;
import com.bilgeadam.utility.JwtTokenManager;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService extends ServiceManager<Auth, Long> {
    private final IAuthRepository authRepository;
    private final UserRegisterProducer userRegisterProducer;
    private final MailRegisterProducer mailRegisterProducer;
    private final UserForgotPassProducer userForgotPassProducer;
    private final MailForgotPasswordProducer mailForgotPassProducer;
    private final CompanyRegisterProducer companyRegisterProducer;
    private final CompanyManagerRegisterProducer companyManagerRegisterProducer;
    private final GuestRegisterProducer guestRegisterProducer;
    private final JwtTokenManager jwtTokenManager;
    private final GuestMailRegisterProducer guestMailRegisterProducer;

    public AuthService(IAuthRepository authRepository, UserRegisterProducer userRegisterProducer,
                       MailForgotPasswordProducer mailForgotPassProducer, UserForgotPassProducer userForgotPassProducer,
                       MailRegisterProducer mailRegisterProducer, CompanyRegisterProducer companyRegisterProducer,
                       CompanyManagerRegisterProducer companyManagerRegisterProducer, GuestRegisterProducer guestRegisterProducer,
                       GuestMailRegisterProducer guestMailRegisterProducer, JwtTokenManager jwtTokenManager) {
        super(authRepository);
        this.authRepository = authRepository;
        this.userRegisterProducer = userRegisterProducer;
        this.userForgotPassProducer = userForgotPassProducer;
        this.mailRegisterProducer = mailRegisterProducer;
        this.mailForgotPassProducer = mailForgotPassProducer;
        this.companyRegisterProducer = companyRegisterProducer;
        this.companyManagerRegisterProducer = companyManagerRegisterProducer;
        this.guestRegisterProducer = guestRegisterProducer;
        this.guestMailRegisterProducer = guestMailRegisterProducer;
        this.jwtTokenManager = jwtTokenManager;
    }

    public void createEmployee(UserCreateEmployeeModel model) {

        Auth auth = IAuthMapper.INSTANCE.authFromUserAddEmployeeModel(model);
        auth.setActivationLink(CodeGenerator.generateCode());
        if (authRepository.findByUsername(auth.getUsername()).isPresent()) {
            throw new AuthManagerException(ErrorType.BAD_REQUEST);
        }
        auth.setRole(ERole.EMPLOYEE);
        auth = save(auth);
        UserRegisterModel userRegisterModel = IAuthMapper.INSTANCE.FromUserCreateEmployeetoUserRegisterModel(model);
        userRegisterModel.setActivationLink(auth.getActivationLink());
        userRegisterModel.setAuthid(auth.getId());
        userRegisterModel.setRole(auth.getRole());

        userRegisterProducer.sendRegisterProducer(userRegisterModel);
        MailRegisterModel mailRegisterModel = IAuthMapper.INSTANCE.fromAuthToMailRegisterModel(auth);
        mailRegisterModel.setActivationLink(auth.getId() + "-" + auth.getActivationLink());
        System.out.println(mailRegisterModel);
        mailRegisterProducer.sendMailRegister(mailRegisterModel);
    }

    public String login(AuthLoginRequestDto dto) {

        Optional<Auth> optionalAuth = authRepository.findOptionalByCompanyEmail(dto.getCompanyEmail());
        if (optionalAuth.isEmpty()) {
            optionalAuth = authRepository.findOptionalByPersonalEmail(dto.getCompanyEmail());
            if (optionalAuth.isEmpty() || !optionalAuth.get().getStatus().equals(EStatus.ACTIVE) || !optionalAuth.get().getRole().equals(ERole.GUEST)) {
                throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
            }

        }

        if (!optionalAuth.get().getPassword().equals(dto.getPassword())) {
            throw new AuthManagerException(ErrorType.PASSWORDS_NOT_MATCH);
        }
//        if (optionalAuth.isEmpty()) {
//            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
//        }
        if (!optionalAuth.get().getStatus().equals(EStatus.ACTIVE)) {
            throw new AuthManagerException(ErrorType.ACCOUNT_NOT_ACTIVE);
        }
        Optional<String> token = jwtTokenManager.createToken(optionalAuth.get().getId(), optionalAuth.get().getRole());
        if (token.isEmpty()) throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);
        return token.get();
    }

    public String forgotPassword(AuthForgotPasswordRequestDto dto) {
        Optional<Auth> optionalAuth = authRepository.findOptionalByPersonalEmail(dto.getEmail());
        if (optionalAuth.isEmpty()) {
            optionalAuth = authRepository.findOptionalByCompanyEmail(dto.getEmail());
            if (optionalAuth.isEmpty()) {
                throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
            }
        }
        if (optionalAuth.isPresent() && optionalAuth.get().getStatus().equals(EStatus.ACTIVE)) {
            //random password
            String randomPassword = UUID.randomUUID().toString();
            optionalAuth.get().setPassword(randomPassword);
            update(optionalAuth.get());
            UserForgotPassModel userForgotPassModel = UserForgotPassModel.builder().password(randomPassword).authid(optionalAuth.get().getId()).build();
            userForgotPassProducer.userForgotPassword(userForgotPassModel);
            MailForgotPassModel mailForgotPassModel = MailForgotPassModel.builder().personalEmail(optionalAuth.get().getPersonalEmail()).companyEmail(optionalAuth.get().getCompanyEmail()).randomPassword(randomPassword).username(optionalAuth.get().getUsername()).build();
            mailForgotPassProducer.forgotPasswordSendMail(mailForgotPassModel);

            return "New password is:" + optionalAuth.get().getPassword();
        }
        throw new AuthManagerException(ErrorType.ACCOUNT_NOT_ACTIVE);
    }


    public Boolean guestRegister(GuestRegisterRequestDto guestRegisterRequestDto) {
        Auth auth = IAuthMapper.INSTANCE.fromGuestRegisterRequestDtoToAuth(guestRegisterRequestDto);
        auth.setActivationLink(CodeGenerator.generateCode());
        auth.setRole(ERole.GUEST);
        save(auth);
        GuestRegisterModel guestRegisterModel = IAuthMapper.INSTANCE.fromGuestRegisterRequestToGuestRegisterModel(guestRegisterRequestDto);
        guestRegisterModel.setAuthid(auth.getId());
        guestRegisterProducer.sendGuest(guestRegisterModel);
        GuestMailRegisterModel guestMailRegisterModel = IAuthMapper.INSTANCE.fromAuthToGuestMailRegisterModel(auth);
        guestMailRegisterModel.setActivationLink(auth.getId() + "-" + auth.getActivationLink());
        System.out.println(guestMailRegisterModel);
        guestMailRegisterProducer.sendMailRegister(guestMailRegisterModel);
        return true;
    }

    public Auth userActive(String token) {
        Long authid = Long.parseLong(token.split("-")[0]);
        String activationLink = token.split("-")[1];
        Optional<Auth> optionalAuth = authRepository.findOptionalById(authid);
        if (optionalAuth.isEmpty()) {
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (optionalAuth.get().getStatus().equals(EStatus.ACTIVE)) {
            throw new AuthManagerException(ErrorType.ACCOUNT_ALREADY_ACTIVE);
        }
        if (optionalAuth.get().getActivationLink().equals(activationLink)) {
            optionalAuth.get().setStatus(EStatus.ACTIVE);
            update(optionalAuth.get());
            UserRegisterModel userRegisterModel = IAuthMapper.INSTANCE.fromAuthToUserRegisterModel(optionalAuth.get());
            userRegisterModel.setStatus(EStatus.ACTIVE);
            userRegisterProducer.sendRegisterProducer(userRegisterModel);
        } else throw new AuthManagerException(ErrorType.ACCOUNT_NOT_ACTIVE);
        return optionalAuth.get();

    }

    public Boolean companyRegister(CompanyRegisterRequestDto dto) {
        Auth auth = IAuthMapper.INSTANCE.fromCompanyRegisterRequestDtoToAuth(dto);
        auth.setRole(ERole.COMPANY_MANAGER);
        save(auth);
        CompanyRegisterModel companyRegisterModel = IAuthMapper.INSTANCE.fromCompanyRegisterRequestDtoToCompanyRegisterModel(dto);
        String companyId = companyRegisterProducer.createNewCompany(companyRegisterModel);
        CompanyManagerRegisterModel companyManagerRegisterModel = IAuthMapper.INSTANCE.fromCompanyRegisterRequestDtoToCompanyManagerRegisterModel(dto);
        companyManagerRegisterModel.setAuthid(auth.getId());
        companyManagerRegisterModel.setCompanyId(companyId);
        companyManagerRegisterProducer.sendCompanyManager(companyManagerRegisterModel);
        return true;
    }

    public Long saveEmployeeReturnId(AddEmployeeSaveAuthModel addEmployeeSaveAuthModel) {
        Auth auth = IAuthMapper.INSTANCE.fromAddEmployeeSaveAuthModelToAuth(addEmployeeSaveAuthModel);
        save(auth);
        if (auth != null) {
            return auth.getId();
        }
        return null;
    }
}
