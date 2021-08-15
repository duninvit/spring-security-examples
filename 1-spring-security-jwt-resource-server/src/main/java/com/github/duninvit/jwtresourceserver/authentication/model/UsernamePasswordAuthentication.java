package com.github.duninvit.jwtresourceserver.authentication.model;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class
UsernamePasswordAuthentication extends UsernamePasswordAuthenticationToken {

    public UsernamePasswordAuthentication(final Object principal,
                                          final Object credentials) {
        super(principal, credentials);
    }

    public UsernamePasswordAuthentication(final Object principal,
                                          final Object credentials,
                                          final Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
