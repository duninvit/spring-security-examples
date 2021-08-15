package com.github.duninvit.jwtresourceserver.authentication;

import com.github.duninvit.jwtresourceserver.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationServerProxy {

    private final RestTemplate restTemplate;

    @Value("${auth.server.base.url}")
    private String baseUrl;

    public AuthenticationServerProxy(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendAuth(final String username, final String password) {
        String url = baseUrl + "/user/auth";

        var body = new User();
        body.setUsername(username);
        body.setPassword(password);
        var request = new HttpEntity<>(body);

        restTemplate.postForEntity(url, request, Void.class);
    }

    public boolean sendOtp(final String username, final String code) {
        String url = baseUrl + "/otp/check";

        var body = new User();
        body.setUsername(username);
        body.setCode(code);
        var request = new HttpEntity<>(body);

        var response = restTemplate.postForEntity(url, request, Void.class);
        return response
                .getStatusCode()
                .equals(HttpStatus.OK);
    }
}
