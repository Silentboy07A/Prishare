package com.prishare.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public boolean loginUser(LoginRequest request) {

        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if (user.isPresent()) {
            return user.get().getPassword().equals(request.getPassword());
        }

        return false;
    }
}