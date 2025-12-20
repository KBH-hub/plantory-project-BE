package com.zero.plantoryprojectbe.auth.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String membername;
    private String password;
}
