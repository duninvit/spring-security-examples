package com.github.duninvit.jwtresourceserver.authentication;

import com.github.duninvit.jwtresourceserver.authentication.model.OtpAuthentication;
import com.github.duninvit.jwtresourceserver.authentication.model.UsernamePasswordAuthentication;
import com.github.duninvit.jwtresourceserver.authentication.provider.UsernamePasswordAuthenticationProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
public class InitialAuthenticationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(InitialAuthenticationFilter.class);

    private final AuthenticationManager manager;

    @Value("${jwt.signin.key}")
    private String signinKey;

    public InitialAuthenticationFilter(final AuthenticationManager manager) {
        this.manager = manager;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest httpServletRequest,
                                    final HttpServletResponse httpServletResponse,
                                    final FilterChain filterChain) throws ServletException, IOException {
        String username = httpServletRequest.getHeader("username");
        String password = httpServletRequest.getHeader("password");
        String code = httpServletRequest.getHeader("code");

        logger.info(username);
        logger.info(password);
        logger.info(code);

        if (code == null) {
            Authentication authentication = new UsernamePasswordAuthentication(username, password);
            manager.authenticate(authentication);
        } else {
            Authentication authentication = new OtpAuthentication(username, code);
            manager.authenticate(authentication);

            SecretKey key = Keys.hmacShaKeyFor(signinKey.getBytes(StandardCharsets.UTF_8));
            String jwt = Jwts.builder()
                    .claim("username", username)
                    .signWith(key)
                    .compact();

            httpServletResponse.setHeader("Authorization", jwt);
            logger.info(jwt);
        }
    }

    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }
}
