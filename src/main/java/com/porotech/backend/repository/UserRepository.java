package com.porotech.backend.repository;

import com.porotech.backend.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Profile("!test")
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}