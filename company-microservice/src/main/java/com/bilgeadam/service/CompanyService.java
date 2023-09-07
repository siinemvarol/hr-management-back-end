package com.bilgeadam.service;

import com.bilgeadam.dto.request.AddEmployeeCompanyDto;
import com.bilgeadam.dto.request.CompanyIdDto;
import com.bilgeadam.dto.request.CompanyRegisterRequestDto;
import com.bilgeadam.dto.request.CompanyUpdateRequestDto;
import com.bilgeadam.exception.CompanyManagerException;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.mapper.ICompanyMapper;
import com.bilgeadam.rabbitmq.model.AddEmployeeCompanyModel;
import com.bilgeadam.rabbitmq.model.UserCompanyIdModel;
import com.bilgeadam.rabbitmq.model.UserCompanyListModel;
import com.bilgeadam.rabbitmq.model.UserCompanyRegisterModel;
import com.bilgeadam.rabbitmq.producer.AddEmployeeCompanyProducer;
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

    public CompanyService(ICompanyRepository companyRepository, UserCompanyRegisterProducer userRegisterProducer, AddEmployeeCompanyProducer addEmployeeCompanyProducer, UserCompanyIdProducer userCompanyIdProducer) {
        super(companyRepository);
        this.companyRepository = companyRepository;
        this.userRegisterProducer = userRegisterProducer;
        this.userCompanyIdProducer = userCompanyIdProducer;
        this.addEmployeeCompanyProducer=addEmployeeCompanyProducer;
    }

    public Boolean register(CompanyRegisterRequestDto dto) {
        Company company = ICompanyMapper.INSTANCE.fromRegisterDtoToCompany(dto);
        save(company);
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
        System.out.println("listelerin son yeri-Company"+model);
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
}
