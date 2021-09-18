package com.github.duninvit.oauth2jwtasyncresourceserver.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${security.oauth2.resource.jwt.key-uri}")
    private String keySetUri;

    @Value("${security.oauth2.client.client-id}")
    private String clientId;

    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    @Override
    public void configure(final ResourceServerSecurityConfigurer resources) {
        resources.tokenStore(tokenStore());
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        var converter = new JwtAccessTokenConverter() {
            @Override
            public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {
                OAuth2Authentication authentication = super.extractAuthentication(claims);
                authentication.setDetails(claims);
                return authentication;
            }
        };
        converter.setVerifierKey(getKeyFromServer());
        return converter;
    }

    private String getKeyFromServer() {
        RestTemplate keyUriRestTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String username = this.clientId;
        String password = this.clientSecret;
        if (username != null && password != null) {
            byte[] token = Base64.getEncoder()
                    .encode((username + ":" + password).getBytes());
            headers.add("Authorization", "Basic " + new String(token));
        }
        HttpEntity<Void> request = new HttpEntity<>(headers);
        return (String) Objects.requireNonNull(keyUriRestTemplate
                .exchange(this.keySetUri, HttpMethod.GET, request, Map.class).getBody())
                .get("value");
    }
}