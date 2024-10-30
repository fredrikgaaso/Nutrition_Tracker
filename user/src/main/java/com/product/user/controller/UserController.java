package com.product.user.controller;

import com.product.user.model.Users;
import com.product.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:1234")
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
    public void addAdminUser() {
        String userName = "admin";
        int wallet = 10000;
        String password = "admin";
        userService.addAdminUser(userName, wallet, password);
    }
    @GetMapping("/all")
    public List<Users> usersList() {
        return userService.getAllUsers();
    }
}
