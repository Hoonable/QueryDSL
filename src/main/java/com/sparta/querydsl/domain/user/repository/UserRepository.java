package com.sparta.querydsl.domain.user.repository;

import com.sparta.querydsl.domain.user.entity.User;
import com.sparta.querydsl.global.enums.UserStatusEnum;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);
    Optional<User> findByEmail(String email);
    boolean existsByUserId(String userId);
    boolean existsByUserIdAndStatus(String userId, UserStatusEnum status);
}