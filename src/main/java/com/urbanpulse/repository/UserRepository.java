package com.urbanpulse.repository;

import com.urbanpulse.model.User;
import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {

    Optional<User> findByEmail(String email);
}