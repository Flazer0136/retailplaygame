package com.cpro.retailplaygame.repository;

import com.cpro.retailplaygame.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// @Repository annotation marks this interface as a Spring Data repository
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}