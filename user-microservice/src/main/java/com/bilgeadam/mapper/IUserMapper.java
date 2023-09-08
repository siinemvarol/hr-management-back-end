package com.bilgeadam.mapper;

import com.bilgeadam.rabbitmq.model.*;
import com.bilgeadam.repository.entity.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {
    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);
    User fromRegisterModelToUserProfile(final UserRegisterModel model);
    User fromUserCompanyRegisterModelToUser(final UserCompanyRegisterModel model);




    UserCreateEmployeeModel userCreateEmployeeModelfromAddEmployeeCompanyModel(final AddEmployeeCompanyModel model);

    UserCompanyListModel userCompanyListModelFromUser(final User x);
}
