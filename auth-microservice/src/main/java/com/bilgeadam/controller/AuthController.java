package com.bilgeadam.controller;

import com.bilgeadam.dto.request.AuthRegisterRequestDto;
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
public class AuthController {
    private final AuthService authService;

    @PostMapping(REGISTER)
    public ResponseEntity<Auth> register(@RequestBody AuthRegisterRequestDto authRegisterRequestDto){
        System.out.println(authRegisterRequestDto);
        return ResponseEntity.ok(authService.registerSave(authRegisterRequestDto));

    }
}
