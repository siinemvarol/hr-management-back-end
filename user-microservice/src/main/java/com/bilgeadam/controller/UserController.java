package com.bilgeadam.controller;

import com.bilgeadam.dto.request.UserForgotPasswordRequestDto;
import com.bilgeadam.rabbitmq.model.UserForgotPassModel;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bilgeadam.constant.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER)
public class UserController {

    private final UserService userService;

    @PutMapping(FORGOT_PASSWORD)
    public ResponseEntity<Boolean> forgotPassword(@RequestBody UserForgotPassModel userForgotPassModel){
        return ResponseEntity.ok(userService.forgotPassword(userForgotPassModel));
    }

}
