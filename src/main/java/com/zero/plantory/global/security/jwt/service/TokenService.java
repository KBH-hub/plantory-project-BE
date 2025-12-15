package com.zero.plantory.global.security.jwt.service;

import com.zero.plantory.global.security.jwt.TokenProvider;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

    public String createNewAccessToken(String refreshToken) {

        if (!tokenProvider.validateToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long userId = refreshTokenService
                .findByRefreshToken(refreshToken)
                .getMemberId();

        return tokenProvider.createAccessToken(String.valueOf(userId));
    }
}


