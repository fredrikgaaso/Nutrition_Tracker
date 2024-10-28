package com.product.user.controller;

import com.product.user.model.Users;
import com.product.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public Users findOneUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/add")
    public Users addNewUser(@RequestBody Users user) {
        return userService.addNewUser(user);
    }

    @PostMapping("/admin")
    public void adminAddNewUser() {
        String userName = "admin";
        int wallet = 10000;
        userService.addAdminUser(userName, wallet);
    }
}
