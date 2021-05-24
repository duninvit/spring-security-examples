package com.github.duninvit.jwtauthserver.repository;

import com.github.duninvit.jwtauthserver.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {

    Optional<Otp> findOtpByUsername(String username);
}
