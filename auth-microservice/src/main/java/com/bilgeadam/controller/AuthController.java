package com.bilgeadam.controller;

import com.bilgeadam.dto.request.AuthLoginRequestDto;
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

    @PostMapping(LOGIN)
    public ResponseEntity<Boolean> login(@RequestBody AuthLoginRequestDto dto){
        return ResponseEntity.ok(authService.login(dto));
    }


}
