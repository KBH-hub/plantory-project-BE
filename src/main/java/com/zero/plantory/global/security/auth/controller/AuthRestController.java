package com.zero.plantory.global.security.auth.controller;

import com.zero.plantory.domain.profile.dto.MemberResponse;
import com.zero.plantory.global.security.auth.dto.AuthMeResponse;
import com.zero.plantory.global.security.auth.dto.AuthUserResponse;
import com.zero.plantory.global.security.auth.dto.LoginRequest;
import com.zero.plantory.global.security.auth.service.AuthService;
import com.zero.plantory.global.security.jwt.RefreshToken;
import com.zero.plantory.global.security.jwt.TokenProvider;
import com.zero.plantory.global.security.jwt.service.RefreshTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final RefreshTokenService refreshTokenService;
    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse) {
        return ResponseEntity.ok(authService.login(loginRequest, httpServletResponse));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @CookieValue(name = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response
    ) {
        if (refreshToken != null) {
            refreshTokenService.deleteByToken(refreshToken);
        }

        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(
            @CookieValue(name = "refreshToken", required = false) String refreshToken
    ) {
        if (refreshToken == null) {
            return ResponseEntity.status(401).build();
        }

        RefreshToken rt = refreshTokenService.findByRefreshToken(refreshToken);

        Long memberId = rt.getMemberId();

        MemberResponse member = authService.findMemberById(memberId);

        String newAccessToken =
                tokenProvider.createAccessToken(String.valueOf(memberId));

        return ResponseEntity.ok(Map.of(
                "accessToken", newAccessToken,
                "user", Map.of(
                        "memberId", member.getMemberId(),
                        "membername", member.getMembername(),
                        "role", member.getRole()
                )
        ));
    }

    @GetMapping("/me")
    public ResponseEntity<AuthMeResponse> me(
            @CookieValue(name = "refreshToken", required = false) String refreshToken
    ) {
        if (refreshToken == null) {
            return ResponseEntity.status(401).build();
        }

        RefreshToken rt;
        try {
            rt = refreshTokenService.findByRefreshToken(refreshToken);
        } catch (Exception e) {
            log.warn("Invalid refresh token", e);
            return ResponseEntity.status(401).build();
        }

        Long memberId = rt.getMemberId();
        MemberResponse member = authService.findMemberById(memberId);

        if (member == null) {
            return ResponseEntity.status(401).build();
        }

        String accessToken =
                tokenProvider.createAccessToken(String.valueOf(memberId));

        return ResponseEntity.ok(
                AuthMeResponse.builder()
                        .user(
                                AuthUserResponse.builder()
                                        .memberId(member.getMemberId())
                                        .membername(member.getMembername())
                                        .nickname(member.getNickname())
                                        .phone(member.getPhone())
                                        .address(member.getAddress())
                                        .sharingRate(member.getSharingRate())
                                        .skillRate(member.getSkillRate())
                                        .managementRate(member.getManagementRate())
                                        .role(member.getRole())
                                        .stopDay(member.getStopDay())
                                        .build()
                        )
                        .accessToken(accessToken)
                        .build()
        );
    }




}
