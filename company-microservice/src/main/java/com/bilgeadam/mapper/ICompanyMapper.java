package com.bilgeadam.mapper;
import com.bilgeadam.dto.request.CompanyRegisterRequestDto;
import com.bilgeadam.dto.request.CompanyUpdateRequestDto;
import com.bilgeadam.rabbitmq.model.UserCompanyIdModel;
import com.bilgeadam.rabbitmq.model.UserCompanyRegisterModel;
import com.bilgeadam.repository.entity.Company;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICompanyMapper {
    ICompanyMapper INSTANCE = Mappers.getMapper(ICompanyMapper.class);
    Company fromRegisterDtoToCompany(final CompanyRegisterRequestDto dto);
    @Mapping(source = "id",target = "companyId")
    UserCompanyRegisterModel fromCompanyToUserRegisterModel(final Company company);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Company fromCompanyUpdateDto(final CompanyUpdateRequestDto dto, @MappingTarget Company company);
    @Mapping(source = "id",target = "companyId")
    UserCompanyIdModel fromCompanyToUserCompanyIdModel(final Company company);
}
