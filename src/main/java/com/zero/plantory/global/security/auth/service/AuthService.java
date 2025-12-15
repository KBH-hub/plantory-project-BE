package com.zero.plantory.global.security.auth.service;

import com.zero.plantory.global.security.MemberDetail;
import com.zero.plantory.global.security.auth.dto.LoginRequest;
import com.zero.plantory.global.security.jwt.service.RefreshTokenService;
import com.zero.plantory.global.security.jwt.TokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

    public Map<String, String> login(
            LoginRequest request,
            HttpServletResponse response
    ) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getMembername(),
                                request.getPassword()
                        )
                );

        MemberDetail memberDetail =
                (MemberDetail) authentication.getPrincipal();

        Long memberId = memberDetail.getMemberResponse().getMemberId();

        String accessToken =
                tokenProvider.createAccessToken(memberId.toString());

        String refreshToken =
                tokenProvider.createRefreshToken(memberId.toString());

        refreshTokenService.save(memberId, refreshToken);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 5);
//        cookie.setMaxAge(60 * 60 * 24 * 14);
        response.addCookie(cookie);

        return Map.of("accessToken", accessToken);
    }
}


