package com.bilgeadam.controller;

import com.bilgeadam.dto.request.EmployeeInfoUpdateDto;


import com.bilgeadam.dto.request.GetImageDto;

import com.bilgeadam.dto.request.GuestInfoUpdateDto;
import com.bilgeadam.rabbitmq.model.UserCreateEmployeeModel;
import com.bilgeadam.repository.entity.User;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static com.bilgeadam.constant.ApiUrls.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(USER)
public class UserController {

    private final UserService userService;
    @PostMapping(CREATE_EMPLOYEE)
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

    //The user selects a photo from own computer in the frontend.
    //Then the image that comes here is added to resources/images.
    //The name of the added photo is taken and its name is recorded in the user.setavatar database.
    @PostMapping(TRANSFER_PHOTO)
    public String handleFileUpload(@RequestPart("file") MultipartFile file, @RequestParam("userId") Long userId) {
        return userService.changeProfilePhoto(file, userId);
    }

    @GetMapping(GET_IMAGE)
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String fileName) throws IOException {

       GetImageDto getImageDto =userService.getImage(fileName);
        return ResponseEntity.ok()
                .headers(getImageDto.getHeaders())
                .body(getImageDto.getResource());
    }

    @PutMapping(UPDATE_GUEST+"/{authId}")
    public ResponseEntity<Boolean> updateGuestInfo(@RequestBody GuestInfoUpdateDto dto, @PathVariable Long authId){
        return ResponseEntity.ok(userService.updateGuestInfo(dto,authId));
    }
    @GetMapping(GET_NUMBER_OF_EMPLOYEES)
    public ResponseEntity<Integer> getNumberOfEmployees(){
        return ResponseEntity.ok(userService.getNumberOfEmployees());
    }

    @GetMapping(GET_NUMBER_OF_ALL_USERS)
    public ResponseEntity<Integer> getNumberOfAllUsers(){
        return ResponseEntity.ok(userService.getNumberOfAllUsers());
    }

    @GetMapping(GET_ALL_EMPLOYEES_IN_COMPANY+"/{authid}")
    public ResponseEntity<Integer> getAllEmployeesInCompany(@PathVariable Long authid){
        return ResponseEntity.ok(userService.getAllEmployeesInCompany(authid));
    }

}

