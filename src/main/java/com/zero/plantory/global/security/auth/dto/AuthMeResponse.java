package com.zero.plantory.global.security.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthMeResponse {
    private AuthUserResponse user;
    private String accessToken;
}