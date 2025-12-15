package com.zero.plantory.global.security.auth.controller;

import com.zero.plantory.global.security.auth.dto.LoginRequest;
import com.zero.plantory.global.security.auth.service.AuthService;
import com.zero.plantory.global.security.jwt.service.RefreshTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final RefreshTokenService refreshTokenService;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse) {
        return ResponseEntity.ok(authService.login(loginRequest, httpServletResponse));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authorization, HttpServletResponse httpServletResponse) {
        String accessToken = authorization.replace("Bearer ", "");
        refreshTokenService.delete(accessToken);
        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        httpServletResponse.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

}
