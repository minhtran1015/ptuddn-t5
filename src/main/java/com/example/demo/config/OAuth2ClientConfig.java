package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@Configuration
public class OAuth2ClientConfig {

    @Value("${AUTH0_CLIENT_ID:}")
    private String clientId;

    @Value("${AUTH0_CLIENT_SECRET:}")
    private String clientSecret;

    @Value("${AUTH0_ISSUER_URI:}")
    private String issuerUri;

    @Bean
    @ConditionalOnProperty(name = "AUTH0_CLIENT_ID")
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(auth0ClientRegistration());
    }

    private ClientRegistration auth0ClientRegistration() {
        return ClientRegistration.withRegistrationId("auth0")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("http://localhost:8080/login/oauth2/code/auth0")
                .scope("openid", "profile", "email", "offline_access")
                .issuerUri(issuerUri)
                .userNameAttributeName("sub")
                .clientName("Auth0")
                .build();
    }
}

