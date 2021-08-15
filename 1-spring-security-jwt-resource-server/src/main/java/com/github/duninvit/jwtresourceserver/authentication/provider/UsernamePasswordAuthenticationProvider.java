package com.github.duninvit.jwtresourceserver.authentication.provider;

import com.github.duninvit.jwtresourceserver.authentication.AuthenticationServerProxy;
import com.github.duninvit.jwtresourceserver.authentication.model.UsernamePasswordAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationServerProxy proxy;

    private final Logger logger = LoggerFactory.getLogger(UsernamePasswordAuthenticationProvider.class);

    UsernamePasswordAuthenticationProvider(final AuthenticationServerProxy proxy) {
        this.proxy = proxy;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        proxy.sendAuth(username, password);

        logger.info(username);
        logger.info(password);
        return new UsernamePasswordAuthentication(username, password);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthentication.class.isAssignableFrom(aClass);
    }
}
