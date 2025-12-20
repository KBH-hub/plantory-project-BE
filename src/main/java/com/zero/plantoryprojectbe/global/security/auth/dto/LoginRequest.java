package com.zero.plantoryprojectbe.global.security.auth.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String membername;
    private String password;
}
