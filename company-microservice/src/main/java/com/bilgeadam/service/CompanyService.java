package com.bilgeadam.service;

import com.bilgeadam.dto.request.AddCommentRequestDto;
import com.bilgeadam.dto.request.AddEmployeeCompanyDto;
import com.bilgeadam.dto.request.CompanyUpdateRequestDto;
import com.bilgeadam.dto.response.GetCompanyInformationResponseDto;
import com.bilgeadam.exception.CompanyManagerException;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.mapper.ICompanyMapper;
import com.bilgeadam.rabbitmq.model.*;
import com.bilgeadam.rabbitmq.producer.AddCommentSaveCommentProducer;
import com.bilgeadam.rabbitmq.producer.AddEmployeeCompanyProducer;
import com.bilgeadam.rabbitmq.producer.GetCompanyInformationProducer;
import com.bilgeadam.rabbitmq.producer.UserCompanyIdProducer;
import com.bilgeadam.repository.ICompanyRepository;
import com.bilgeadam.repository.entity.Company;
import com.bilgeadam.repository.enums.EStatus;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CompanyService extends ServiceManager<Company, String> {
    private final ICompanyRepository companyRepository;
    private final UserCompanyIdProducer userCompanyIdProducer;
    private final AddEmployeeCompanyProducer addEmployeeCompanyProducer;
    private final AddCommentSaveCommentProducer addCommentSaveCommentProducer;
    private final GetCompanyInformationProducer getCompanyInformationProducer;

    public CompanyService(ICompanyRepository companyRepository,
                          AddEmployeeCompanyProducer addEmployeeCompanyProducer, UserCompanyIdProducer userCompanyIdProducer,
                          AddCommentSaveCommentProducer addCommentSaveCommentProducer,
                          GetCompanyInformationProducer getCompanyInformationProducer) {
        super(companyRepository);
        this.companyRepository = companyRepository;
        this.userCompanyIdProducer = userCompanyIdProducer;
        this.addEmployeeCompanyProducer = addEmployeeCompanyProducer;
        this.addCommentSaveCommentProducer = addCommentSaveCommentProducer;
        this.getCompanyInformationProducer = getCompanyInformationProducer;
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
        Optional<Company> optionalCompany = companyRepository.findById(addEmployeeCompanyDto.getCompanyId());
        if (optionalCompany.isEmpty()) {
            throw new CompanyManagerException(ErrorType.INVALID_COMPANY);
        }
        AddEmployeeCompanyModel addEmployeeCompanyModel = ICompanyMapper.INSTANCE.addEmployeeCompanyModelfromAddEmployeeCompanyDto(addEmployeeCompanyDto);
        String companyEmail = addEmployeeCompanyModel.getName() + addEmployeeCompanyModel.getSurname() + "@" + optionalCompany.get().getCompanyName() + ".com";

        String[] mailArray = companyEmail.toLowerCase().split(" ");
        companyEmail = "";
        for (String part : mailArray) {
            companyEmail = companyEmail + part;
        }

        addEmployeeCompanyModel.setCompanyEmail(companyEmail);

//        addEmployeeCompanyModel.setCompanyId(optionalCompany.get().getId());
        addEmployeeCompanyProducer.sendAddEmployeeMessage(addEmployeeCompanyModel);

        return null;
    }

    public Boolean createNewCompany(CompanyRegisterModel companyRegisterModel) {
        companyRepository.save(ICompanyMapper.INSTANCE.fromCompanyRegisterModelToCompany(companyRegisterModel));
        return true;
    }

    // method for adding new comment to a company by an employee
    // first it checks if user is authorized to make comment to company
    // then it saves the comment
    public Boolean addComment(AddCommentRequestDto addCommentRequestDto) {
        addCommentSaveCommentProducer.sendCommentToSave(ICompanyMapper.INSTANCE.fromAddCommentRequestDtoToAddCommentSaveCommentModel(addCommentRequestDto));
        return true;
    }

    public Integer getNumberCompany() {
        return companyRepository.findAll().size();
    }

    public List<Company> getNewNumberCompany() {
        Long date = System.currentTimeMillis()-86400000;
        return companyRepository.findByCreateDateAfter(date);
    }

    public List<Company> getCompanies() {
        return  companyRepository.findAll();
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
