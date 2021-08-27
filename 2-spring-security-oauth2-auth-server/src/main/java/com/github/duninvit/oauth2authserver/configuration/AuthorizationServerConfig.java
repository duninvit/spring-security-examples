package com.github.duninvit.oauth2authserver.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${jwt.key}")
    private String jwtKey;

    @Value("${jwt.client-id}")
    private String clientId;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.resource-server-client-id}")
    private String recourceServerClientId;

    @Value("${jwt.resource-server-secret}")
    private String recourceServerSecret;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthorizationServerConfig(final AuthenticationManager authenticationManager,
                                     final PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(clientId)
                .secret(passwordEncoder.encode(secret))
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("read")
                .and()
                .withClient(recourceServerClientId)
                .secret(passwordEncoder.encode(recourceServerSecret));
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .authenticationManager(authenticationManager);
    }

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer security) {
        security.checkTokenAccess("isAuthenticated()");
    }
}
