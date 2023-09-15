package com.bilgeadam.controller;

import com.bilgeadam.dto.request.AddCommentRequestDto;
import com.bilgeadam.dto.request.AddEmployeeCompanyDto;
import com.bilgeadam.dto.request.CompanyUpdateRequestDto;
import com.bilgeadam.dto.response.GetCompanyInformationResponseDto;
import com.bilgeadam.rabbitmq.model.AddEmployeeCompanyModel;
import com.bilgeadam.rabbitmq.model.UserCompanyIdModel;
import com.bilgeadam.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

import static com.bilgeadam.constant.ApiUrls.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(COMPANY)
@CrossOrigin(origins = "http://localhost:3000")
public class CompanyController {
    private final CompanyService companyService;

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

    @PostMapping(ADD_COMMENT)
    public ResponseEntity<Boolean> addComment(@RequestBody AddCommentRequestDto addCommentRequestDto){
        System.out.println("company controller is working...");
        return ResponseEntity.ok(companyService.addComment(addCommentRequestDto));
    }
    @GetMapping(GET_COMPANY_INFORMATION+"/{companyId}")
    public ResponseEntity<GetCompanyInformationResponseDto> getCompanyInformation(@PathVariable String companyId){
        return ResponseEntity.ok(companyService.getCompanyInformation(companyId));
    }
}
