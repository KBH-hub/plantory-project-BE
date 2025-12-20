package com.zero.plantoryprojectbe.auth.dto;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private Long refreshTokenId;
    private Long memberId;
    private String tokenHash;
}
