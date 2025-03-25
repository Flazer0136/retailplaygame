package com.cpro.retailplaygame.repository;

import com.cpro.retailplaygame.entity.Authorities;
import com.cpro.retailplaygame.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface AuthoritiesRepository extends JpaRepository<Authorities, Long> {
    List<Authorities> findByUser(User user);
}