package com.bilgeadam.service;

import com.bilgeadam.dto.request.AddEmployeeCompanyDto;
import com.bilgeadam.dto.request.CompanyRegisterRequestDto;
import com.bilgeadam.dto.request.CompanyUpdateRequestDto;
import com.bilgeadam.exception.CompanyManagerException;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.mapper.ICompanyMapper;
import com.bilgeadam.rabbitmq.model.*;
import com.bilgeadam.rabbitmq.producer.AddEmployeeCompanyProducer;
import com.bilgeadam.rabbitmq.producer.UserCompanyIdProducer;
import com.bilgeadam.repository.ICompanyRepository;
import com.bilgeadam.repository.entity.Company;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService extends ServiceManager<Company, String> {
    private final ICompanyRepository companyRepository;
    private final UserCompanyIdProducer userCompanyIdProducer;
    private final AddEmployeeCompanyProducer addEmployeeCompanyProducer;

    public CompanyService(ICompanyRepository companyRepository,
                          AddEmployeeCompanyProducer addEmployeeCompanyProducer, UserCompanyIdProducer userCompanyIdProducer) {
        super(companyRepository);
        this.companyRepository = companyRepository;
        this.userCompanyIdProducer = userCompanyIdProducer;
        this.addEmployeeCompanyProducer = addEmployeeCompanyProducer;
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
        String companyEmail =addEmployeeCompanyModel.getName()+addEmployeeCompanyModel.getSurname()+"@"+optionalCompany.get().getCompanyName()+".com";
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
