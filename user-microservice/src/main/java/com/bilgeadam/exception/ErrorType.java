package com.bilgeadam.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    // User Service Error
    BAD_REQUEST(4000, "Parametre hatası", HttpStatus.BAD_REQUEST),
    LOGIN_ERROR(4001, "Kullanıcı adı veya şifre hatalı", HttpStatus.BAD_REQUEST),
    USERNAME_DUPLICATE(4002, "Bu kullanıcı zaten kayıtlı", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4003, "User not found.", HttpStatus.NOT_FOUND),
    ACCOUNT_NOT_ACTIVE(4004, "Hesabınız aktif değil.", HttpStatus.BAD_REQUEST),
    ACTIVATE_CODE_ERROR(4005, "Aktivasyon kodu hatası", HttpStatus.BAD_REQUEST),
    ALREADY_ACTIVE(4006, "Kullanıcı zaten aktif", HttpStatus.BAD_GATEWAY),
    PASSWORD_ERROR(4007,"Şifreler uyuşmuyor.", HttpStatus.BAD_REQUEST),
    USER_NOT_AUTHORIZED(4008,"User is not authorized to make comment for this company.", HttpStatus.BAD_REQUEST),

    // Service Error
    INTERNAL_ERROR(5000, "Sunucu hatası", HttpStatus.INTERNAL_SERVER_ERROR),;

    private int code;
    private String message;
    HttpStatus httpStatus;
}
