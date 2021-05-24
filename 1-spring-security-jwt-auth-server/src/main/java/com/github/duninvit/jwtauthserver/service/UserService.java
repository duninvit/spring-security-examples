package com.github.duninvit.jwtauthserver.service;

import com.github.duninvit.jwtauthserver.model.Otp;
import com.github.duninvit.jwtauthserver.model.User;
import com.github.duninvit.jwtauthserver.repository.OtpRepository;
import com.github.duninvit.jwtauthserver.repository.UserRepository;
import com.github.duninvit.jwtauthserver.util.GenerateCodeUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final OtpRepository otpRepository;

    public UserService(
            final PasswordEncoder passwordEncoder,
            final UserRepository userRepository,
            final OtpRepository otpRepository
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.otpRepository = otpRepository;
    }

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void auth(User inUser) {
        var userOptional = userRepository.findUserByUsername(inUser.getUsername());

        userOptional.ifPresentOrElse(user -> {
            if (passwordEncoder.matches(inUser.getPassword(), user.getPassword())) {
                renewOpt(user);
            } else {
                throw new BadCredentialsException("Bad credentials");
            }
        }, () -> {
            throw new BadCredentialsException("Bad credentials");
        });
    }

    public boolean check(Otp inOtp) {
        var otpOptional = otpRepository.findOtpByUsername(inOtp.getUsername());

        if (otpOptional.isPresent()) {
            var otp = otpOptional.get();
            return inOtp.getCode().equals(otp.getCode());
        }

        return false;
    }

    private void renewOpt(User user) {
        String code = GenerateCodeUtil.generateCode();

        var otpOptional = otpRepository.findOtpByUsername(user.getUsername());

        otpOptional.ifPresentOrElse(otp -> {
            otp.setCode(code);
        }, () -> {
            var newOtp = new Otp();
            newOtp.setUsername(user.getUsername());
            newOtp.setCode(code);
            otpRepository.save(newOtp);
        });
    }
}
