package com.lokesh.ai_support_platform.auth.repository;

import com.lokesh.ai_support_platform.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);

    Optional<User> getUserByUserName(String userName);

    Optional<User> findById(Long id);
}
