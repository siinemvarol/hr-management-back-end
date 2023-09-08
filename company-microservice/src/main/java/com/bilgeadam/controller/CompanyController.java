package com.bilgeadam.controller;

import com.bilgeadam.dto.request.AddEmployeeCompanyDto;
import com.bilgeadam.dto.request.CompanyRegisterRequestDto;
import com.bilgeadam.dto.request.CompanyUpdateRequestDto;
import com.bilgeadam.rabbitmq.model.AddEmployeeCompanyModel;
import com.bilgeadam.rabbitmq.model.UserCompanyIdModel;
import com.bilgeadam.repository.entity.Company;
import com.bilgeadam.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;

import static com.bilgeadam.constant.ApiUrls.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(COMPANY)
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping(COMPANY_REGISTER)
    public ResponseEntity<Boolean> register(@RequestBody CompanyRegisterRequestDto dto){
        return ResponseEntity.ok(companyService.register(dto));
    }
    @PostMapping(UPDATE)
    public ResponseEntity<Boolean> update(@RequestBody @Valid CompanyUpdateRequestDto dto){
        return ResponseEntity.ok(companyService.updateCompany(dto));
    }
    @PostMapping(GETUSERS)
    public ResponseEntity<Boolean> getUserList(@RequestBody @Valid UserCompanyIdModel model){
        return ResponseEntity.ok(companyService.sendCompanyId(model));
    }
    @PostMapping(ADDEMPLOYEE)
    public ResponseEntity<AddEmployeeCompanyModel> addEmployee(@RequestBody AddEmployeeCompanyDto addEmployeeCompanyDto){
        return ResponseEntity.ok(companyService.addEmployee(addEmployeeCompanyDto));
    }
}
