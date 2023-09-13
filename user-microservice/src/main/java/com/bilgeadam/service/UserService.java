package com.bilgeadam.service;

import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.UserManagerException;
import com.bilgeadam.mapper.IUserMapper;

import com.bilgeadam.rabbitmq.model.*;
import com.bilgeadam.rabbitmq.producer.AddEmloyeeProducer;
import com.bilgeadam.rabbitmq.producer.UserCompanyIdModelsProducer;
import com.bilgeadam.rabbitmq.model.UserCreateEmployeeModel;
import com.bilgeadam.rabbitmq.model.UserForgotPassModel;
import com.bilgeadam.rabbitmq.model.UserRegisterModel;
import com.bilgeadam.repository.IUserRepository;
import com.bilgeadam.repository.entity.User;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatus;
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

    public UserService(IUserRepository userRepository, AddEmloyeeProducer addEmloyeeProducer, UserCompanyIdModelsProducer userCompanyIdModelsProducer) {

        super(userRepository);
        this.userRepository = userRepository;
        this.addEmloyeeProducer = addEmloyeeProducer;
        this.userCompanyIdModelsProducer = userCompanyIdModelsProducer;
    }

    public Boolean createUser(UserRegisterModel model) {
        Optional<User> optionalUser = userRepository.findOptionalByUsername(model.getUsername());
        if (optionalUser.isPresent()) {
            optionalUser.get().setStatus(EStatus.ACTIVE);
            update(optionalUser.get());
            return true;

        } else {
            User user = userRepository.save(IUserMapper.INSTANCE.fromRegisterModelToUserProfile(model));
            user.setRole(model.getRole());
            System.out.println(user);
            return true;
        }


    }

    public void forgotPassword(UserForgotPassModel userForgotPassModel) {

        Optional<User> optionalUser = userRepository.findOptionalByAuthid(userForgotPassModel.getAuthid());

        if (optionalUser.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        optionalUser.get().setPassword(userForgotPassModel.getPassword());
        update(optionalUser.get());
    }

    public UserCreateEmployeeModel createEmployee(UserCreateEmployeeModel userCreateEmployeeModel) {
        if (userRepository.findOptionalByUsername(userCreateEmployeeModel.getUsername()).isPresent()) {
            throw new UserManagerException(ErrorType.USERNAME_DUPLICATE);
        }
        addEmloyeeProducer.sendAddEmployee(userCreateEmployeeModel);
        return userCreateEmployeeModel;
    }




    public void addEmployeeCompany(AddEmployeeCompanyModel model) {
        UserCreateEmployeeModel userCreateEmployeeModel = IUserMapper.INSTANCE.userCreateEmployeeModelfromAddEmployeeCompanyModel(model);

        createEmployee(userCreateEmployeeModel);
    }


    public void findByCompanyId(UserCompanyIdModel model) {
        List<UserCompanyListModel> companyIdModels = new ArrayList<>();
        List<User> userList = userRepository.findByCompanyId(model.getCompanyId());
        userList.forEach(x -> {
            UserCompanyListModel userCompanyListModel = IUserMapper.INSTANCE.userCompanyListModelFromUser(x);
            companyIdModels.add(userCompanyListModel);
        });
        userCompanyIdModelsProducer.sendUserList(companyIdModels);
    }


    // saving company manager as user (when registering new company)
    public Boolean createNewCompanyManager(CompanyManagerRegisterModel companyManagerRegisterModel) {
        User user = IUserMapper.INSTANCE.fromCompanyManagerRegisterModelToUser(companyManagerRegisterModel);
        user.setRole(ERole.COMPANY_MANAGER);
        save(user);
        return true;
    }

    public Optional<User> findEmployeeByAuthId(Long authId) {
        Optional<User> employee = userRepository.findOptionalByAuthid(authId);
        if (employee.isPresent()) {
            System.out.println(employee.get());
            return employee;
        } else {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
    }

    // saving guest as user (when guest is registering)
    public Boolean createNewGuest(GuestRegisterModel guestRegisterModel) {
        User user = IUserMapper.INSTANCE.fromGuestRegisterModelToUser(guestRegisterModel);
        user.setRole(ERole.GUEST);
        save(user);
        return true;
    }
}
