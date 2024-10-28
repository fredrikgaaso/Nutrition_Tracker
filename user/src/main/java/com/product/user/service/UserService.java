package com.product.user.service;

import com.product.user.model.Users;
import com.product.user.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService  {

    private final UserRepo userRepo;

    public Users getUserById(Long id){
       return userRepo.getUserById(id);
    }

    public Users addNewUser(Users user){
        log.info("dette er brukeren som blir lagt til: " + user.getName() + user.getId());
        userRepo.save(user);
        return user;
    }

    public void addAdminUser(String userName, int wallet){
        Users user = new Users();
        user.setName(userName);
        user.setWallet(wallet);
        userRepo.save(user);
    }
}
