package com.urbanpulse.service;

import com.urbanpulse.exception.AuthenticationException;
import com.urbanpulse.exception.DuplicateEmailException;
import com.urbanpulse.model.User;
import com.urbanpulse.model.enums.UserRole;
import com.urbanpulse.repository.UserRepository;
import com.urbanpulse.util.PasswordUtil;
import com.urbanpulse.util.ValidationUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String name, String email, String plainPassword, UserRole role) {
        if (!ValidationUtil.isNotBlank(name)) {
            throw new IllegalArgumentException("Name is required");
        }
        if (!ValidationUtil.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!ValidationUtil.hasMinLength(plainPassword, 8)) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new DuplicateEmailException("Email already registered: " + email);
        }

        String hashedPassword = PasswordUtil.hash(plainPassword);
        User user = new User(name, email, hashedPassword, role);
        return userRepository.save(user);
    }

    public User login(String email, String plainPassword) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            throw new AuthenticationException("Invalid email or password");
        }

        User user = userOpt.get();
        if (!PasswordUtil.verify(plainPassword, user.getPasswordHash())) {
            throw new AuthenticationException("Invalid email or password");
        }

        return user;
    }
}