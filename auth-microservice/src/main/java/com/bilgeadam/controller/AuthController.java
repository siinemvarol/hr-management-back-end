package com.bilgeadam.controller;

import com.bilgeadam.dto.request.AuthForgotPasswordRequestDto;
import com.bilgeadam.dto.request.AuthLoginRequestDto;
import com.bilgeadam.dto.request.AuthRegisterRequestDto;
import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.response.AuthRegisterResponseDto;
import com.bilgeadam.repository.entity.Auth;
import com.bilgeadam.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bilgeadam.constant.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final AuthService authService;

    @PostMapping(LOGIN)
    public ResponseEntity<Boolean> login(@RequestBody AuthLoginRequestDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping(REGISTER)
    public ResponseEntity<RegisterRequestDto> register(@RequestBody RegisterRequestDto registerRequestDto) {
        return ResponseEntity.ok(authService.registerSave(registerRequestDto));
    }

    @PostMapping(FORGOT_PASSWORD)
    public ResponseEntity<String> forgotPassword(@RequestBody AuthForgotPasswordRequestDto dto) {
        return ResponseEntity.ok(authService.forgotPassword(dto));
    }
    @GetMapping(USER_ACTIVE)
    public Auth userActive(String token){
        return authService.userActive(token);
    }


}
