package com.bilgeadam.service;

import com.bilgeadam.dto.request.AddCommentRequestDto;
import com.bilgeadam.dto.request.AddEmployeeCompanyDto;
import com.bilgeadam.dto.request.CompanyUpdateRequestDto;
import com.bilgeadam.dto.response.GetCompanyInformationResponseDto;
import com.bilgeadam.mapper.ICompanyMapper;
import com.bilgeadam.rabbitmq.model.*;
import com.bilgeadam.rabbitmq.producer.*;
import com.bilgeadam.repository.ICompanyRepository;
import com.bilgeadam.repository.entity.Company;
import com.bilgeadam.repository.enums.ERole;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService extends ServiceManager<Company, String> {
    private final ICompanyRepository companyRepository;
    private final UserCompanyIdProducer userCompanyIdProducer;
    private final AddEmployeeCompanyProducer addEmployeeCompanyProducer;
    private final AddCommentSaveCommentProducer addCommentSaveCommentProducer;
    private final GetCompanyInformationProducer getCompanyInformationProducer;
    private final AddCommentGetUserAndCompanyProducer addCommentGetUserAndCompanyProducer;
    private final AddEmployeeGetCompanyIdProducer addEmployeeGetCompanyIdProducer;
    private final AddEmployeeSaveAuthProducer addEmployeeSaveAuthProducer;
    private final AddEmployeeSaveUserProducer addEmployeeSaveUserProducer;

    public CompanyService(ICompanyRepository companyRepository,
                          AddEmployeeCompanyProducer addEmployeeCompanyProducer, UserCompanyIdProducer userCompanyIdProducer,
                          AddCommentSaveCommentProducer addCommentSaveCommentProducer,
                          GetCompanyInformationProducer getCompanyInformationProducer,
                          AddCommentGetUserAndCompanyProducer addCommentGetUserAndCompanyProducer,
                          AddEmployeeGetCompanyIdProducer addEmployeeGetCompanyIdProducer,
                          AddEmployeeSaveAuthProducer addEmployeeSaveAuthProducer,
                          AddEmployeeSaveUserProducer addEmployeeSaveUserProducer) {
        super(companyRepository);
        this.companyRepository = companyRepository;
        this.userCompanyIdProducer = userCompanyIdProducer;
        this.addEmployeeCompanyProducer = addEmployeeCompanyProducer;
        this.addCommentSaveCommentProducer = addCommentSaveCommentProducer;
        this.getCompanyInformationProducer = getCompanyInformationProducer;
        this.addCommentGetUserAndCompanyProducer = addCommentGetUserAndCompanyProducer;
        this.addEmployeeGetCompanyIdProducer = addEmployeeGetCompanyIdProducer;
        this.addEmployeeSaveAuthProducer = addEmployeeSaveAuthProducer;
        this.addEmployeeSaveUserProducer = addEmployeeSaveUserProducer;
    }

    public Boolean updateCompany(CompanyUpdateRequestDto dto) {
        Optional<Company> optionalCompany = companyRepository.findById(dto.getCompanyId());
        if (optionalCompany.isPresent()) {
            save(ICompanyMapper.INSTANCE.fromCompanyUpdateDto(dto, optionalCompany.get()));
            return true;
        }
        throw new RuntimeException("hata");
    }

    public Boolean sendCompanyId(UserCompanyIdModel model) {
        userCompanyIdProducer.sendCompanyIdMessage(model);
        return true;
    }

    public List<UserCompanyListModel> userListCompany(List<UserCompanyListModel> model) {
        return model;
    }

    public AddEmployeeCompanyModel addEmployee(AddEmployeeCompanyDto addEmployeeCompanyDto) {
        AddEmployeeGetCompanyIdModel getCompanyNameModel = new AddEmployeeGetCompanyIdModel();
        getCompanyNameModel.setAuthid(addEmployeeCompanyDto.getAuthid());
        String companyId = addEmployeeGetCompanyIdProducer.toUserGetCompanyId(getCompanyNameModel);
        System.out.println("company id is..." + companyId);
        Optional<Company> optionalCompany = findById(companyId);
        AddEmployeeSaveAuthModel authModel = ICompanyMapper.INSTANCE.fromAddEmployeeCompanyDtoToAuthModel(addEmployeeCompanyDto);
        String companyEmail = addEmployeeCompanyDto.getName() + addEmployeeCompanyDto.getSurname() + "@" + optionalCompany.get().getCompanyName() + ".com";
        String[] mailArray = companyEmail.toLowerCase().split(" ");
        companyEmail = "";
        for (String part : mailArray) {
            companyEmail = companyEmail + part;
        }
        authModel.setCompanyEmail(companyEmail);
        authModel.setRole(ERole.EMPLOYEE);
        System.out.println("auth model to save is...: " + authModel);
        Long employeeAuthId = addEmployeeSaveAuthProducer.toAuthSaveEmployee(authModel);
        AddEmployeeSaveUserModel userModel = ICompanyMapper.INSTANCE.fromAddEmployeeCompanyDtoToAddEmployeeSaveUserModel(addEmployeeCompanyDto);
        userModel.setCompanyEmail(companyEmail);
        userModel.setRole(ERole.EMPLOYEE);
        userModel.setAuthid(employeeAuthId);
        userModel.setCompanyId(optionalCompany.get().getId());
        addEmployeeSaveUserProducer.toUserSaveEmployee(userModel);

//        Optional<Company> optionalCompany = companyRepository.findById(addEmployeeCompanyDto.getCompanyId());
//        if (optionalCompany.isEmpty()) {
//            throw new CompanyManagerException(ErrorType.INVALID_COMPANY);
//        }
//        AddEmployeeCompanyModel addEmployeeCompanyModel = ICompanyMapper.INSTANCE.addEmployeeCompanyModelfromAddEmployeeCompanyDto(addEmployeeCompanyDto);
//        String companyEmail = addEmployeeCompanyModel.getName() + addEmployeeCompanyModel.getSurname() + "@" + optionalCompany.get().getCompanyName() + ".com";
//
//        String[] mailArray = companyEmail.toLowerCase().split(" ");
//        companyEmail = "";
//        for (String part : mailArray) {
//            companyEmail = companyEmail + part;
//        }
//        addEmployeeCompanyModel.setCompanyEmail(companyEmail);
////        addEmployeeCompanyModel.setCompanyId(optionalCompany.get().getId());
//        addEmployeeCompanyProducer.sendAddEmployeeMessage(addEmployeeCompanyModel);
        return null;
    }

    public String createNewCompany(CompanyRegisterModel companyRegisterModel) {
        Company company = ICompanyMapper.INSTANCE.fromCompanyRegisterModelToCompany(companyRegisterModel);
        save(company);
        return company.getId();
    }

    // method for adding new comment to a company by an employee
    public Boolean addComment(AddCommentRequestDto addCommentRequestDto) {
        AddCommentUserAndCompanyResponseModel responseModel = addCommentGetUserAndCompanyProducer.getUserIdAndCompanyId(ICompanyMapper.INSTANCE.fromAddCommentRequestDtoToAddCommentGetUserAndCompanyModel(addCommentRequestDto.getAuthid()));
        AddCommentSaveCommentModel addCommentSaveCommentModel = ICompanyMapper.INSTANCE.fromAddCommentRequestDtoToAddCommentSaveCommentModel(addCommentRequestDto);
        addCommentSaveCommentModel.setCompanyId(responseModel.getCompanyId());
        addCommentSaveCommentModel.setUserId(responseModel.getUserId());
        addCommentSaveCommentProducer.sendCommentToSave(addCommentSaveCommentModel);
        return true;
    }

    public Integer getNumberCompany() {
        return companyRepository.findAll().size();
    }

    public List<Company> getNewNumberCompany() {
        Long date = System.currentTimeMillis() - 86400000;
        return companyRepository.findByCreateDateAfter(date);
    }

    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    public List<Company> getNotAuthorizedCompanies() {
        return companyRepository.findByStatus(EStatus.NOT_AUTHORIZED);
    }

    public Boolean activateCompany(String id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()) {
            optionalCompany.get().setStatus(EStatus.ACTIVE);
            update(optionalCompany.get());
            return true;
        }
        throw new RuntimeException("hata");
    }

    public Boolean deniedCompany(String id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (optionalCompany.isPresent()) {
            optionalCompany.get().setStatus(EStatus.DENIED);
            update(optionalCompany.get());
            return true;
        }
        throw new RuntimeException("hata");
    }


    public GetCompanyInformationResponseDto getCompanyInformation(Long authid) {
        GetCompanyInformationModel getCompanyInformationModel = new GetCompanyInformationModel();
        getCompanyInformationModel.setAuthid(authid);
        String companyId = getCompanyInformationProducer.sendAuthIdToUser(getCompanyInformationModel);
        Optional<Company> optionalCompany = findById(companyId);
        GetCompanyInformationResponseDto getCompanyInformationResponseDto = ICompanyMapper.INSTANCE.fromCompanyToGetCompanyInformationResponseDto(optionalCompany.get());
        return getCompanyInformationResponseDto;

    }

}
