package com.product.user.controller;

import com.product.user.model.user;
import com.product.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public user getOneUser(Long id) {
        return null;
    }

    public user addNewUser(user user) {
        return user;
    }
}
