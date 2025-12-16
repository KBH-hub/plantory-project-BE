package com.zero.plantory.global.security.jwt;

import lombok.*;

@Data
public class RefreshToken {
    private Long refreshTokenId;
    private Long memberId;
    private String tokenHash;
//    private String refreshToken;
}
