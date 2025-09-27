package com.example.Manage.Student.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Manage.Student.Entity.BlackListToken;

public interface BlackListRepository extends JpaRepository<BlackListToken, Long> {
    Optional<BlackListToken> findByToken(String token);
}
