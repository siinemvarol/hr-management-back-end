package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.AuthRegisterRequestDto;
import com.bilgeadam.dto.response.AuthRegisterResponseDto;
import com.bilgeadam.rabbitmq.model.MailRegisterModel;
import com.bilgeadam.rabbitmq.model.UserRegisterModel;
import com.bilgeadam.repository.entity.Auth;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAuthMapper {
    IAuthMapper INSTANCE = Mappers.getMapper(IAuthMapper.class);

    Auth fromRegisterDto(final AuthRegisterRequestDto dto);

    @Mapping(source = "id", target = "authid")
    UserRegisterModel fromAuthToUserRegisterModel(final Auth auth);
    MailRegisterModel fromAuthToMailRegisterModel(final Auth auth);

    AuthRegisterResponseDto fromAuth(final Auth auth);


}
