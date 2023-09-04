package com.bilgeadam.mapper;

import com.bilgeadam.repository.entity.User;
import com.bilgeadam.rabbitmq.model.UserRegisterModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {
    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);
    User fromRegisterModelToUserProfile(final UserRegisterModel model);


}
