package com.bilgeadam.controller;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.repository.entity.Auth;
import com.bilgeadam.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.io.IOException;

import static com.bilgeadam.constant.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
//@CrossOrigin(origins = "*",allowedHeaders = "*")
public class AuthController {
    private final AuthService authService;

    @PostMapping(LOGIN)
    public ResponseEntity<String> login(@RequestBody AuthLoginRequestDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping(GUEST_REGISTER)
    public ResponseEntity<Boolean> guestRegister(@RequestBody GuestRegisterRequestDto guestRegisterRequestDto) {
        return ResponseEntity.ok(authService.guestRegister(guestRegisterRequestDto));
    }

    @PostMapping(FORGOT_PASSWORD)
    public ResponseEntity<String> forgotPassword(@RequestBody AuthForgotPasswordRequestDto dto) {
        return ResponseEntity.ok(authService.forgotPassword(dto));
    }
    @GetMapping(USER_ACTIVE)
    public String userActive(String token) throws IOException {
        return authService.userActive(token);
    }

    @PostMapping(COMPANY_REGISTER)
    public ResponseEntity<Boolean> companyRegister(@RequestBody  CompanyRegisterRequestDto dto) {
        return ResponseEntity.ok(authService.companyRegister(dto));
    }


}
