package com.github.duninvit.oauth2jwtasyncauthserver.configuration;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.time.ZoneId;
import java.util.Map;

public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(final OAuth2AccessToken accessToken,
                                     final OAuth2Authentication authentication) {
        var token = new DefaultOAuth2AccessToken(accessToken);
        Map<String, Object> info = Map.of("generatedInZone", ZoneId.systemDefault().toString());
        token.setAdditionalInformation(info);
        return token;
    }
}
