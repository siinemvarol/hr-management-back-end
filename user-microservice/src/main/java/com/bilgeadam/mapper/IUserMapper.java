package com.bilgeadam.mapper;

import com.bilgeadam.rabbitmq.model.UserCompanyRegisterModel;
import com.bilgeadam.rabbitmq.model.AuthRegisterModel;
import com.bilgeadam.repository.entity.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {
    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);
    User fromRegisterModelToUserProfile(final AuthRegisterModel model);
    User fromUserCompanyRegisterModelToUser(final UserCompanyRegisterModel model);

}
