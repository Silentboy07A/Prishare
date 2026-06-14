
package com.prishare.backend.service;

import com.prishare.backend.dto.LoginRequest;
import com.prishare.backend.model.User;
import com.prishare.backend.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User registerUser(User user) {

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        return userRepository.save(user);
    }

    public boolean loginUser(LoginRequest request) {

        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if (user.isPresent()) {
            return passwordEncoder.matches(
                    request.getPassword(),
                    user.get().getPassword()
            );
        }

        return false;
    }
}