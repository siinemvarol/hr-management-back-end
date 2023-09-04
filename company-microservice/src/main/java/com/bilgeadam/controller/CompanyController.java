package com.bilgeadam.controller;

import com.bilgeadam.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.bilgeadam.constant.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(COMPANY)
public class CompanyController {
    private final CompanyService companyService;
}
