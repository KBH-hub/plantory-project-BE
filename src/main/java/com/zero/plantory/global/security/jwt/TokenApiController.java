package com.zero.plantory.global.security.jwt;

import com.zero.plantory.global.security.jwt.dto.CreateAccessTokenRequest;
import com.zero.plantory.global.security.jwt.dto.CreateAccessTokenResponse;
import com.zero.plantory.global.security.jwt.service.RefreshTokenService;
import com.zero.plantory.global.security.jwt.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class TokenApiController {
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(
            @RequestBody CreateAccessTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }

    @DeleteMapping("/api/refreshToken")
    public ResponseEntity<Void> deleteRefreshToken(Authentication authentication) {

        Long userId = Long.valueOf(authentication.getName());
        refreshTokenService.deleteByUserId(userId);

        return ResponseEntity.ok().build();
    }

}
