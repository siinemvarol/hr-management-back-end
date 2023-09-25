package com.bilgeadam.controller;

import com.bilgeadam.dto.request.EmployeeInfoUpdateDto;
//import com.bilgeadam.dto.request.GuestInfoUpdateDto;
import com.bilgeadam.rabbitmq.model.UserCreateEmployeeModel;
import com.bilgeadam.repository.entity.User;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.bilgeadam.constant.ApiUrls.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(USER)
//@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    //   Geçici Süreliğine Devre DISIYIZZZ!!(K:T)
    //    @PutMapping(FORGOT_PASSWORD)
    //    public ResponseEntity<Boolean> forgotPassword(@RequestBody UserForgotPassModel userForgotPassModel){
    //        return ResponseEntity.ok(userService.forgotPassword(userForgotPassModel));
    //    }
    @PostMapping(CREATEEMPLOYEE)
    public ResponseEntity<UserCreateEmployeeModel> addEmployee(@RequestBody UserCreateEmployeeModel userAddEmployeeModel){
        return ResponseEntity.ok(userService.createEmployee(userAddEmployeeModel));
    }
    @GetMapping(FIND_BY_ID+"/{authId}")
    public ResponseEntity<Optional<User>> findEmployeeByAuthId(@PathVariable Long authId){
        return ResponseEntity.ok(userService.findEmployeeByAuthId(authId));
    }

    @PutMapping(UPDATE+"/{authId}")
    public ResponseEntity<Boolean> updateEmployeeInfo(@RequestBody EmployeeInfoUpdateDto dto, @PathVariable Long authId){
        return ResponseEntity.ok(userService.updateEmployeeInfo(dto,authId));
    }
//    @PutMapping(UPDATE_GUEST+"/{authId}")
//    public ResponseEntity<Boolean> updateGuestInfo(@RequestBody GuestInfoUpdateDto dto, @PathVariable Long authId){
//        return ResponseEntity.ok(userService.updateGuestInfo(dto,authId));
//    }

}

