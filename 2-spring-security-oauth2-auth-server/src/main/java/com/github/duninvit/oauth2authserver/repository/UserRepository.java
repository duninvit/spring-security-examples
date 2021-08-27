package com.github.duninvit.oauth2authserver.repository;


import com.github.duninvit.oauth2authserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);
}
