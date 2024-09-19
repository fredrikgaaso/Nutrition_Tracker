package com.product.user.service;

import com.product.user.model.user;
import com.product.user.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService  {

    private final UserRepo userRepo;
    user getOneUser(Long id){
        return null;
    }
    user addNewUser(user user){
        return user;
    }
}
