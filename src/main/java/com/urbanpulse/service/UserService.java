package com.urbanpulse.service;

import com.urbanpulse.exception.ResourceNotFoundException;
import com.urbanpulse.model.User;
import com.urbanpulse.repository.UserRepository;
import com.urbanpulse.util.ValidationUtil;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
    }

    public User updateProfile(Long id, String name, String email) {
        User user = getById(id);
        if (ValidationUtil.isNotBlank(name)) {
            user.setName(name);
        }
        if (ValidationUtil.isValidEmail(email)) {
            user.setEmail(email);
        }
        return userRepository.update(user);
    }
}