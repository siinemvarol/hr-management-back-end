package com.bilgeadam.controller;

import com.bilgeadam.dto.request.EmployeeInfoUpdateDto;


import com.bilgeadam.dto.request.GetImageDto;

import com.bilgeadam.dto.request.GuestInfoUpdateDto;
import com.bilgeadam.rabbitmq.model.UserCreateEmployeeModel;
import com.bilgeadam.repository.entity.User;
import com.bilgeadam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    //Kullanıcı frontende bilgisayarından foto seçerek Backende image olarak gönderiyor burada
    //Daha sonra buraya gelen image resources/images e ekleniyor.
    //ekelen fotonun ismi alınarak user.setavatar databaseye isi kayıt ediliyor.
    @PostMapping(TRANSFER_PHOTO)
    public String handleFileUpload(@RequestPart("file") MultipartFile file, @RequestParam("userId") Long userId) {
        return userService.changeProfilePhoto(file, userId);
    }

    //burda da end point olarak frontendden gelen user id si ile image bulunuyor dosyalardan.
    // bulunan image response olarak dönülüyor.
    //dönülen url sayesinde profil fotoğrafı değiştiriliyor.
    //burada bir nvi kendi cloudumuzu kullanıyoruz
   // kodun işlemesi için herkesin kendi resources/images adresini userServicedeki pathe eklemesi gerek.

    @GetMapping(GET_IMAGE)
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String fileName) throws IOException {

       GetImageDto getImageDto =userService.getImage(fileName);

        // ResponseEntity oluştur
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

