package com.bilgeadam.service;

import com.bilgeadam.dto.request.CompanyIdDto;
import com.bilgeadam.dto.request.CompanyRegisterRequestDto;
import com.bilgeadam.dto.request.CompanyUpdateRequestDto;
import com.bilgeadam.mapper.ICompanyMapper;
import com.bilgeadam.rabbitmq.model.UserCompanyIdModel;
import com.bilgeadam.rabbitmq.model.UserCompanyListModel;
import com.bilgeadam.rabbitmq.model.UserCompanyRegisterModel;
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

    public CompanyService(ICompanyRepository companyRepository, UserCompanyRegisterProducer userRegisterProducer, UserCompanyIdProducer userCompanyIdProducer) {
        super(companyRepository);
        this.companyRepository = companyRepository;
        this.userRegisterProducer = userRegisterProducer;
        this.userCompanyIdProducer = userCompanyIdProducer;
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
        System.out.println(model);
        return model;
    }
}
