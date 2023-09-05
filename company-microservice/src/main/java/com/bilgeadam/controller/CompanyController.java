package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CompanyRegisterRequestDto;
import com.bilgeadam.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

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

    @PutMapping(ADDEMPLOYEE)
    public ResponseEntity<?> addEmployee(@PathVariable Long companyId, String userId) {
        return null;
    }

    @PostMapping(REGISTER)
    public ResponseEntity<Boolean> register(@RequestBody @Valid CompanyRegisterRequestDto dto){
        return ResponseEntity.ok(companyService.register(dto));
    }
}
