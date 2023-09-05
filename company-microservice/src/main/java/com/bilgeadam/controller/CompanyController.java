package com.bilgeadam.controller;

import com.bilgeadam.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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


}
