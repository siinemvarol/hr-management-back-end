package com.bilgeadam.service;

import com.bilgeadam.dto.request.AddEmployeeCompanyDto;
import com.bilgeadam.dto.request.CompanyRegisterRequestDto;
import com.bilgeadam.dto.request.CompanyUpdateRequestDto;
import com.bilgeadam.exception.CompanyManagerException;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.mapper.ICompanyMapper;
import com.bilgeadam.rabbitmq.model.*;
import com.bilgeadam.rabbitmq.producer.AddEmployeeCompanyProducer;
import com.bilgeadam.rabbitmq.producer.RegisterCompanyManagerProducer;
import com.bilgeadam.rabbitmq.producer.UserCompanyIdProducer;
import com.bilgeadam.rabbitmq.producer.UserCompanyRegisterProducer;
import com.bilgeadam.repository.ICompanyRepository;
import com.bilgeadam.repository.entity.Company;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService extends ServiceManager<Company, String> {
    private final ICompanyRepository companyRepository;
    private final UserCompanyRegisterProducer userRegisterProducer;
    private final UserCompanyIdProducer userCompanyIdProducer;
    private final AddEmployeeCompanyProducer addEmployeeCompanyProducer;
    private final RegisterCompanyManagerProducer registerCompanyManagerProducer;

    public CompanyService(ICompanyRepository companyRepository, UserCompanyRegisterProducer userRegisterProducer, RegisterCompanyManagerProducer registerCompanyManagerProducer,
                          AddEmployeeCompanyProducer addEmployeeCompanyProducer, UserCompanyIdProducer userCompanyIdProducer) {
        super(companyRepository);
        this.companyRepository = companyRepository;
        this.userRegisterProducer = userRegisterProducer;
        this.userCompanyIdProducer = userCompanyIdProducer;
        this.addEmployeeCompanyProducer = addEmployeeCompanyProducer;
        this.registerCompanyManagerProducer =registerCompanyManagerProducer;
    }
    public Boolean register(CompanyRegisterRequestDto dto) {
        Company company = ICompanyMapper.INSTANCE.fromRegisterDtoToCompany(dto);
        save(company);
        RegisterCompanyManagerModel registerCompanyManagerModel = ICompanyMapper.INSTANCE.registerCompanyManagerModelFromDto(dto);
        registerCompanyManagerModel.setCompanyEmail(dto.getName()+dto.getSurname()+"@"+company.getName()+".com");
        registerCompanyManagerModel.setCompanyId(company.getId());
        registerCompanyManagerModel.setUsername(dto.getUsername());
        registerCompanyManagerProducer.sendRegisterCompanyManagerModel(registerCompanyManagerModel);
        userRegisterProducer.sendRegisterMessage(ICompanyMapper.INSTANCE.fromCompanyToUserRegisterModel(company));
        return true;
    }
    public Boolean updateCompany(CompanyUpdateRequestDto dto) {
        Optional<Company> optionalCompany = companyRepository.findById(dto.getCompanyId());
        if (optionalCompany.isPresent()) {
            save(ICompanyMapper.INSTANCE.fromCompanyUpdateDto(dto, optionalCompany.get()));
            return true;
        }
        throw new RuntimeException("hata");
    }

    public Boolean sendCompanyId(UserCompanyIdModel model){
        userCompanyIdProducer.sendCompanyIdMessage(model);
        return true;
    }

    public List<UserCompanyListModel> userListCompany(List<UserCompanyListModel> model) {
        return model;
    }

    public AddEmployeeCompanyModel addEmployee(AddEmployeeCompanyDto addEmployeeCompanyDto) {
        Optional<Company> optionalCompany = companyRepository.findById(addEmployeeCompanyDto.getCompanyId());
        if (optionalCompany.isEmpty()){
            throw new CompanyManagerException(ErrorType.INVALID_COMPANY);
        }
        AddEmployeeCompanyModel addEmployeeCompanyModel = ICompanyMapper.INSTANCE.addEmployeeCompanyModelfromAddEmployeeCompanyDto(addEmployeeCompanyDto);
        String companyEmail =addEmployeeCompanyModel.getName()+addEmployeeCompanyModel.getSurname()+"@"+optionalCompany.get().getName()+".com";
        addEmployeeCompanyModel.setCompanyEmail(companyEmail);
        addEmployeeCompanyModel.setCompanyId(optionalCompany.get().getId());
        addEmployeeCompanyProducer.sendAddEmployeeMessage(addEmployeeCompanyModel);

        return null;
    }

    public Boolean createNewCompany(CompanyRegisterModel companyRegisterModel){
        companyRepository.save(ICompanyMapper.INSTANCE.fromCompanyRegisterModelToCompany(companyRegisterModel));
        return true;
    }
}
