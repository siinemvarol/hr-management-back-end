package com.bilgeadam.controller;

import com.bilgeadam.dto.request.UserForgotPasswordRequestDto;
import com.bilgeadam.rabbitmq.model.UserAddEmployeeModel;
import com.bilgeadam.rabbitmq.model.UserForgotPassModel;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bilgeadam.constant.ApiUrls.*;
import static com.bilgeadam.constant.ApiUrls.ADDEMPLOYEE;

@RestController
@RequiredArgsConstructor
@RequestMapping(USER)
public class UserController {

    private final UserService userService;

    @PutMapping(FORGOT_PASSWORD)
    public ResponseEntity<Boolean> forgotPassword(@RequestBody UserForgotPassModel userForgotPassModel){
        return ResponseEntity.ok(userService.forgotPassword(userForgotPassModel));
    }
    @PostMapping(ADDEMPLOYEE)
    public ResponseEntity<UserAddEmployeeModel> addEmployee(@RequestBody UserAddEmployeeModel userAddEmployeeModel){
        return ResponseEntity.ok(userService.addEmployee(userAddEmployeeModel));
    }

}
