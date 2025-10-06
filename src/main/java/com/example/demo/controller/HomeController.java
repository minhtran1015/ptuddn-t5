package com.example.demo.controller;

import com.example.demo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired(required = false)
    private TokenService tokenService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/profile")
    @ResponseBody
    public Map<String, Object> profile(@AuthenticationPrincipal OidcUser principal, Authentication authentication) {
        if (principal == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Not authenticated");
            error.put("message", "Please configure Auth0 credentials and login first");
            return error;
        }

        Map<String, Object> profile = new HashMap<>();
        profile.put("sub", principal.getSubject());
        profile.put("name", principal.getFullName());
        profile.put("email", principal.getEmail());
        profile.put("claims", principal.getClaims());
        profile.put("idToken", principal.getIdToken().getTokenValue());

        if (tokenService != null) {
            profile.put("tokenInfo", tokenService.getTokenInfo(authentication));
        }
        return profile;
    }

    @GetMapping("/tokens")
    @ResponseBody
    public Map<String, Object> tokens(Authentication authentication) {
        if (tokenService == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "OAuth2 not configured");
            error.put("message", "Please set AUTH0_CLIENT_ID, AUTH0_CLIENT_SECRET, and AUTH0_ISSUER_URI environment variables");
            return error;
        }

        if (authentication == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Not authenticated");
            error.put("message", "Please login first");
            return error;
        }

        return tokenService.getTokenInfo(authentication);
    }

    @GetMapping("/login")
    public String login() {
        return "redirect:/oauth2/authorization/auth0";
    }
}
