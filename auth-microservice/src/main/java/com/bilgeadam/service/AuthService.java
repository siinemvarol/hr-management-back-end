package com.bilgeadam.service;

import com.bilgeadam.repository.IAuthRepository;
import com.bilgeadam.repository.entity.Auth;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class AuthService extends ServiceManager<Auth,Long> {

    private final IAuthRepository authRepository;
    public AuthService(IAuthRepository authRepository){
        super(authRepository);
        this.authRepository = authRepository;
    }
}
