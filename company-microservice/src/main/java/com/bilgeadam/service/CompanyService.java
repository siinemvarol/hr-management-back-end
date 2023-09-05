package com.bilgeadam.service;

import com.bilgeadam.dto.request.CompanyRegisterRequestDto;
import com.bilgeadam.mapper.ICompanyMapper;
import com.bilgeadam.rabbitmq.producer.UserCompanyRegisterProducer;
import com.bilgeadam.repository.ICompanyRepository;
import com.bilgeadam.repository.entity.Company;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class CompanyService extends ServiceManager<Company, String> {
    private final ICompanyRepository companyRepository;
    private final UserCompanyRegisterProducer userRegisterProducer;

    public CompanyService(ICompanyRepository companyRepository, UserCompanyRegisterProducer userRegisterProducer) {
        super(companyRepository);
        this.companyRepository = companyRepository;
        this.userRegisterProducer = userRegisterProducer;
    }

    public Boolean register(CompanyRegisterRequestDto dto) {
        Company company = ICompanyMapper.INSTANCE.fromRegisterDtoToCompany(dto);
        save(company);
        userRegisterProducer.sendRegisterMessage(ICompanyMapper.INSTANCE.fromCompanyToUserRegisterModel(company));
        return true;
    }
}
