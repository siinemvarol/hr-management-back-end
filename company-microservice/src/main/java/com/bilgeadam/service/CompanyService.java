package com.bilgeadam.service;

import com.bilgeadam.dto.request.AddCommentRequestDto;
import com.bilgeadam.dto.request.AddEmployeeCompanyDto;
import com.bilgeadam.dto.request.CompanyUpdateRequestDto;
import com.bilgeadam.dto.response.GetAllCopmpaniesInformationResponseDto;
import com.bilgeadam.dto.response.GetCompanyInformationManagerResponseDto;
import com.bilgeadam.dto.response.GetCompanyInformationResponseDto;
import com.bilgeadam.dto.response.GetCompanyValuationManagerResponseDto;
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
import java.util.stream.Collectors;

@Service
public class CompanyService extends ServiceManager<Company, String> {
    private final ICompanyRepository companyRepository;
    private final UserCompanyIdProducer userCompanyIdProducer;
    private final AddCommentSaveCommentProducer addCommentSaveCommentProducer;
    private final GetCompanyInformationProducer getCompanyInformationProducer;
    private final AddCommentGetUserAndCompanyProducer addCommentGetUserAndCompanyProducer;
    private final AddEmployeeGetCompanyIdProducer addEmployeeGetCompanyIdProducer;
    private final AddEmployeeSaveAuthProducer addEmployeeSaveAuthProducer;
    private final AddEmployeeSaveUserProducer addEmployeeSaveUserProducer;
    private final GetCompanyInformationManagerProducer getCompanyInformationManagerProducer;
    private final GetCompanyValuationManagerProducer getCompanyValuationManagerProducer;

    public CompanyService(ICompanyRepository companyRepository,
                          UserCompanyIdProducer userCompanyIdProducer,
                          AddCommentSaveCommentProducer addCommentSaveCommentProducer,
                          GetCompanyInformationProducer getCompanyInformationProducer,
                          AddCommentGetUserAndCompanyProducer addCommentGetUserAndCompanyProducer,
                          AddEmployeeGetCompanyIdProducer addEmployeeGetCompanyIdProducer,
                          AddEmployeeSaveAuthProducer addEmployeeSaveAuthProducer,
                          AddEmployeeSaveUserProducer addEmployeeSaveUserProducer,
                          GetCompanyInformationManagerProducer getCompanyInformationManagerProducer,
                          GetCompanyValuationManagerProducer getCompanyValuationManagerProducer) {
        super(companyRepository);
        this.companyRepository = companyRepository;
        this.userCompanyIdProducer = userCompanyIdProducer;
        this.addCommentSaveCommentProducer = addCommentSaveCommentProducer;
        this.getCompanyInformationProducer = getCompanyInformationProducer;
        this.addCommentGetUserAndCompanyProducer = addCommentGetUserAndCompanyProducer;
        this.addEmployeeGetCompanyIdProducer = addEmployeeGetCompanyIdProducer;
        this.addEmployeeSaveAuthProducer = addEmployeeSaveAuthProducer;
        this.addEmployeeSaveUserProducer = addEmployeeSaveUserProducer;
        this.getCompanyInformationManagerProducer = getCompanyInformationManagerProducer;
        this.getCompanyValuationManagerProducer = getCompanyValuationManagerProducer;
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

    public Boolean addEmployee(AddEmployeeCompanyDto addEmployeeCompanyDto) {
        AddEmployeeGetCompanyIdModel getCompanyNameModel = new AddEmployeeGetCompanyIdModel();
        getCompanyNameModel.setAuthid(addEmployeeCompanyDto.getAuthid());
        String companyId = addEmployeeGetCompanyIdProducer.toUserGetCompanyId(getCompanyNameModel);
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
        Long employeeAuthId = addEmployeeSaveAuthProducer.toAuthSaveEmployee(authModel);
        AddEmployeeSaveUserModel userModel = ICompanyMapper.INSTANCE.fromAddEmployeeCompanyDtoToAddEmployeeSaveUserModel(addEmployeeCompanyDto);
        userModel.setCompanyEmail(companyEmail);
        userModel.setRole(ERole.EMPLOYEE);
        userModel.setAuthid(employeeAuthId);
        userModel.setCompanyId(optionalCompany.get().getId());
        addEmployeeSaveUserProducer.toUserSaveEmployee(userModel);
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

    // returns company name for getting pending comments (comment service)
    public String returnCompanyName(GetPendingCommentsCompanyNameModel getPendingCommentsCompanyNameModel) {
        Optional<Company> optionalCompany = findById(getPendingCommentsCompanyNameModel.getId());
        return optionalCompany.get().getCompanyName();
    }

    public GetCompanyInformationManagerResponseDto getCompanyInformationManager(Long authid) {
        GetCompanyInformationManagerModel getCompanyInformationManagerModel = new GetCompanyInformationManagerModel();
        getCompanyInformationManagerModel.setAuthid(authid);
        String companyId = getCompanyInformationManagerProducer.returnCompanyIdManager(getCompanyInformationManagerModel);
        Optional<Company> optionalCompany = findById(companyId);
        GetCompanyInformationManagerResponseDto responseDto = ICompanyMapper.INSTANCE.fromCompanyToGetCompanyInformationManagerResponseDto(optionalCompany.get());
        return responseDto;

    }

    public GetCompanyValuationManagerResponseDto getCompanyValuationManager(Long authid) {
        GetCompanyValuationManagerModel getCompanyValuationManagerModel = new GetCompanyValuationManagerModel();
        getCompanyValuationManagerModel.setAuthid(authid);
        String companyId = getCompanyValuationManagerProducer.returnCompanyIdManagerValuation(getCompanyValuationManagerModel);
        Optional<Company> optionalCompany = findById(companyId);
        GetCompanyValuationManagerResponseDto responseDto = ICompanyMapper.INSTANCE.fromCompanyToGetCompanyValuationManagerResponseDto(optionalCompany.get());
        return responseDto;
    }

    public List<Company> getActiveCompanies() {
        List<Company> activeCompanies = companyRepository.findCompaniesByStatus(EStatus.ACTIVE);
        return activeCompanies;
    }


    public List<GetAllCopmpaniesInformationResponseDto> getAllCopmpaniesInformationResponseDto() {
        List<Company> companies = getActiveCompanies();
        List<GetAllCopmpaniesInformationResponseDto> companiesDtos = companies.stream()
                .map(company -> ICompanyMapper.INSTANCE.fromCompanyGetAllCopmpaniesInformationResponseDto(company))
                .collect(Collectors.toList());
        return  companiesDtos;
    }

}
