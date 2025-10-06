package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
@ConditionalOnBean(ClientRegistrationRepository.class)
public class TokenService {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Autowired(required = false)
    private OAuth2AuthorizedClientManager authorizedClientManager;

    public Map<String, Object> getTokenInfo(Authentication authentication) {
        Map<String, Object> tokenInfo = new HashMap<>();

        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {

            OAuth2AuthorizedClient authorizedClient = authorizedClientService
                .loadAuthorizedClient(
                    oauthToken.getAuthorizedClientRegistrationId(),
                    oauthToken.getName()
                );

            if (authorizedClient != null) {
                OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
                OAuth2RefreshToken refreshToken = authorizedClient.getRefreshToken();

                tokenInfo.put("accessToken", accessToken.getTokenValue());
                tokenInfo.put("accessTokenType", accessToken.getTokenType().getValue());
                tokenInfo.put("accessTokenExpiresAt", accessToken.getExpiresAt());
                tokenInfo.put("accessTokenIsExpired", accessToken.getExpiresAt() != null && accessToken.getExpiresAt().isBefore(Instant.now()));

                if (refreshToken != null) {
                    tokenInfo.put("refreshToken", refreshToken.getTokenValue());
                    tokenInfo.put("refreshTokenExpiresAt", refreshToken.getExpiresAt());
                    tokenInfo.put("hasRefreshToken", true);
                } else {
                    tokenInfo.put("hasRefreshToken", false);
                }

                tokenInfo.put("scopes", accessToken.getScopes());
            }
        }

        return tokenInfo;
    }
}
