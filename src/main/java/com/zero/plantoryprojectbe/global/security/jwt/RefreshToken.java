package com.zero.plantoryprojectbe.global.security.jwt;

import lombok.Data;

@Data
public class RefreshToken {
    private Long refreshTokenId;
    private Long memberId;
    private String tokenHash;
//    private String refreshToken;
}
