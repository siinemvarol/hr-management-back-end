package com.bilgeadam.mapper;
import com.bilgeadam.dto.request.AddCommentRequestDto;
import com.bilgeadam.dto.request.AddEmployeeCompanyDto;
import com.bilgeadam.dto.request.CompanyUpdateRequestDto;
import com.bilgeadam.dto.response.GetCompanyInformationResponseDto;
import com.bilgeadam.rabbitmq.model.*;
import com.bilgeadam.repository.entity.Company;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICompanyMapper {
    ICompanyMapper INSTANCE = Mappers.getMapper(ICompanyMapper.class);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Company fromCompanyUpdateDto(final CompanyUpdateRequestDto dto, @MappingTarget Company company);
    @Mapping(source = "id",target = "companyId")
    UserCompanyIdModel fromCompanyToUserCompanyIdModel(final Company company);

    Company fromCompanyRegisterModelToCompany(final CompanyRegisterModel companyRegisterModel);

    AddCommentSaveCommentModel fromAddCommentRequestDtoToAddCommentSaveCommentModel(final AddCommentRequestDto addCommentRequestDto);

    GetCompanyInformationResponseDto fromCompanyToGetCompanyInformationResponseDto(final Company company);

    AddCommentGetUserAndCompanyModel fromAddCommentRequestDtoToAddCommentGetUserAndCompanyModel(final Long authid);

    AddEmployeeSaveAuthModel fromAddEmployeeCompanyDtoToAuthModel(final AddEmployeeCompanyDto addEmployeeCompanyDto);

    AddEmployeeSaveUserModel fromAddEmployeeCompanyDtoToAddEmployeeSaveUserModel(final AddEmployeeCompanyDto addEmployeeCompanyDto);
}
