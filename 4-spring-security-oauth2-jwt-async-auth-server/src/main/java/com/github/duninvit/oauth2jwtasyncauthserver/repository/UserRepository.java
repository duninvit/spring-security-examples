package com.github.duninvit.oauth2jwtasyncauthserver.repository;

import com.github.duninvit.oauth2jwtasyncauthserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);
}
