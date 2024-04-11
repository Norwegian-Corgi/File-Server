package com.norwegiancorgi.fileserver.internal.services;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    public String getEmailFromToken(JwtAuthenticationToken token) {
        final Jwt jwt = (Jwt) token.getPrincipal();
        return jwt.getClaims().get("email").toString();
    }
}
