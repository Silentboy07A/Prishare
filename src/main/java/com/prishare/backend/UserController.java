package com.prishare.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        boolean success = userService.loginUser(request);

        if (success) {
            return "Login Successful";
        }

        return "Invalid Credentials";
    }
}