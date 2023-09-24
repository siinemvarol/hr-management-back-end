package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.AuthRegisterRequestDto;
import com.bilgeadam.dto.request.CompanyRegisterRequestDto;
import com.bilgeadam.dto.request.GuestRegisterRequestDto;
import com.bilgeadam.dto.response.AuthRegisterResponseDto;
import com.bilgeadam.rabbitmq.model.*;
import com.bilgeadam.repository.entity.Auth;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {
    IAuthMapper INSTANCE = Mappers.getMapper(IAuthMapper.class);

    Auth fromRegisterDto(final AuthRegisterRequestDto dto);


    MailRegisterModel fromAuthToMailRegisterModel(final Auth auth);

    AuthRegisterResponseDto fromAuth(final Auth auth);

    AuthRegisterResponseDto fromAuthToRegisterResponseDto(final Auth auth);

    @Mapping(source = "id", target = "authid")
    UserForgotPassModel fromAuthToUserForgotPassModel(final Auth auth);

    Auth authFromUserAddEmployeeModel(final UserCreateEmployeeModel model);

    @Mapping(source = "password", target = "randomPassword")
    MailForgotPassModel fromAuthToMailForgotPassModel(final Auth auth);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Auth fromCompanyRegisterRequestDtoToAuth(final CompanyRegisterRequestDto dto);

    CompanyRegisterModel fromCompanyRegisterRequestDtoToCompanyRegisterModel(final CompanyRegisterRequestDto dto);

    CompanyManagerRegisterModel fromAuthToCompanyManagerRegisterModel(final Auth auth);

    CompanyManagerRegisterModel fromCompanyRegisterRequestDtoToCompanyManagerRegisterModel(final CompanyRegisterRequestDto dto);

    Auth fromGuestRegisterRequestDtoToAuth(final GuestRegisterRequestDto guestRegisterRequestDto);

    GuestRegisterModel fromGuestRegisterRequestToGuestRegisterModel(final GuestRegisterRequestDto guestRegisterRequestDto);

    UserRegisterModel UserRegisterModelFromUserCreateEmployee(final UserCreateEmployeeModel model);

    UserRegisterModel fromAuthToUserRegisterModel(final Auth auth);

    UserRegisterModel FromUserCreateEmployeetoUserRegisterModel(final UserCreateEmployeeModel model);

    GuestMailRegisterModel fromAuthToGuestMailRegisterModel(final Auth auth);

    Auth fromAddEmployeeSaveAuthModelToAuth(final AddEmployeeSaveAuthModel addEmployeeSaveAuthModel);

    AddEmployeeMailModel fromAuthToAddEmployeeMailModel(final Auth auth);
}
