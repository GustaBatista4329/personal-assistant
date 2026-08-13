package com.gustavo.personalassistant.repository;

import com.gustavo.personalassistant.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findById(UUID id);

    boolean existsByEmail(String email);
}
