package com.bilgeadam.controller;

import com.bilgeadam.dto.request.AddCommentRequestDto;
import com.bilgeadam.dto.request.AddEmployeeCompanyDto;
import com.bilgeadam.dto.request.CompanyUpdateRequestDto;
import com.bilgeadam.dto.response.GetAllCopmpaniesInformationResponseDto;
import com.bilgeadam.dto.response.GetCompanyInformationManagerResponseDto;
import com.bilgeadam.dto.response.GetCompanyInformationResponseDto;
import com.bilgeadam.dto.response.GetCompanyValuationManagerResponseDto;
import com.bilgeadam.rabbitmq.model.UserCompanyIdModel;
import com.bilgeadam.repository.entity.Company;
import com.bilgeadam.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

import java.util.List;

import static com.bilgeadam.constant.ApiUrls.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(COMPANY)
@CrossOrigin(origins = "*" ,allowedHeaders = "*")
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping(UPDATE)
    public ResponseEntity<Boolean> update(@RequestBody @Valid CompanyUpdateRequestDto dto){
        return ResponseEntity.ok(companyService.updateCompany(dto));
    }
    @PostMapping(GET_USERS)
    public ResponseEntity<Boolean> getUserList(@RequestBody @Valid UserCompanyIdModel model){
        return ResponseEntity.ok(companyService.sendCompanyId(model));
    }
    @PostMapping(ADD_EMPLOYEE+"/{authid}")
    public ResponseEntity<Boolean> addEmployee(@RequestBody AddEmployeeCompanyDto addEmployeeCompanyDto){
        return ResponseEntity.ok(companyService.addEmployee(addEmployeeCompanyDto));
    }

    @PostMapping(ADD_COMMENT+"/{authid}")
    public ResponseEntity<Boolean> addComment(@RequestBody AddCommentRequestDto addCommentRequestDto){
        System.out.println("company controller is working...");
        return ResponseEntity.ok(companyService.addComment(addCommentRequestDto));
    }


    @GetMapping(GET_NUMBER_COMPANY)
    public ResponseEntity<Integer> getNumberCompany(){
        return ResponseEntity.ok(companyService.getNumberCompany());
    }


    @GetMapping(GET_NEW_NUMBER_COMPANY)
    public ResponseEntity<List<Company>> getNewNumberCompany(){
        return ResponseEntity.ok(companyService.getNewNumberCompany());
    }

    @GetMapping(GET_COMPANIES)
    public ResponseEntity<List<Company>> getCompanies(){
        return ResponseEntity.ok(companyService.getCompanies());
    }

    @GetMapping(GET_NOT_AUTHORIZED_COMPANIES)
    public ResponseEntity<List<Company>> getNotAuthorizedCompanies(){
        return ResponseEntity.ok(companyService.getNotAuthorizedCompanies());
    }


    @PutMapping(ACTIVATE_COMPANY)
    public ResponseEntity<Boolean> activateCompany(String id){
        return ResponseEntity.ok(companyService.activateCompany(id));
    }

    @PutMapping(DENIED_COMPANY)
    public ResponseEntity<Boolean> deniedCompany(String id){
        return ResponseEntity.ok(companyService.deniedCompany(id));
    }

//    @GetMapping(GET_COMPANY_INFORMATION+"/{companyId}")
//    public ResponseEntity<GetCompanyInformationResponseDto> getCompanyInformation(@PathVariable String companyId){
//        return ResponseEntity.ok(companyService.getCompanyInformation(companyId));
//    }
    // for employee's my company page
    @GetMapping(GET_COMPANY_INFORMATION+"/{authid}")
    public ResponseEntity<GetCompanyInformationResponseDto> getCompanyInformation(@PathVariable Long authid){
        return ResponseEntity.ok(companyService.getCompanyInformation(authid));
    }

    // for company manager's company information page
    @GetMapping(GET_COMPANY_INFORMATION_MANAGER+"/{authid}")
    public ResponseEntity<GetCompanyInformationManagerResponseDto> getCompanyInformationManager(@PathVariable Long authid){
        return ResponseEntity.ok(companyService.getCompanyInformationManager(authid));
    }

    // for company manager's company information page
    @GetMapping(GET_COMPANY_VALUATION_MANAGER+"/{authid}")
    public ResponseEntity<GetCompanyValuationManagerResponseDto> getCompanyValuationManager(@PathVariable Long authid){
        return ResponseEntity.ok(companyService.getCompanyValuationManager(authid));
    }
    // for guest companies information page
    @GetMapping(ACTIVATE_COMPANIES_LIST)
    public ResponseEntity<List<GetAllCopmpaniesInformationResponseDto>> getAllCompaniesInformation(){
        return ResponseEntity.ok(companyService.getAllCopmpaniesInformationResponseDto());
    }


}
