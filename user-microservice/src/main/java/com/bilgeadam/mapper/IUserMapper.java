package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.EmployeeInfoUpdateDto;

//import com.bilgeadam.dto.request.GuestInfoUpdateDto;

import com.bilgeadam.rabbitmq.model.*;
import com.bilgeadam.repository.entity.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserMapper {
    IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);


    User fromRegisterModelToUserProfile(final UserRegisterModel model);

    UserCompanyListModel userCompanyListModelFromUser(final User x);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User fromCompanyManagerRegisterModelToUser(final CompanyManagerRegisterModel companyManagerRegisterModel);

    User fromGuestRegisterModelToUser(final GuestRegisterModel guestRegisterModel);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User fromEmployeeInfoUpdateRequestDtoToUser(final EmployeeInfoUpdateDto dto, @MappingTarget User user);

    User fromAddEmployeeSaveUserModelToUser(final AddEmployeeSaveUserModel addEmployeeSaveUserModel);

  //  User fromGuestInfoUpdateRequestDtoToUser(final GuestInfoUpdateDto dto, @MappingTarget User user);

}
