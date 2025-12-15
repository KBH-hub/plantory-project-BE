package com.zero.plantory.global.security.jwt;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    private Long refreshTokenId;
    private Long memberId;
    private String refreshToken;
}
