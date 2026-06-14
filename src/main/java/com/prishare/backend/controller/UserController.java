
package com.prishare.backend.controller;
import com.prishare.backend.dto.LoginRequest;
import com.prishare.backend.model.User;
import com.prishare.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.prishare.backend.security.JwtUtil;
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
        return JwtUtil.generateToken(request.getEmail());
    }

    return "Invalid Credentials";
} 
}
