package com.zero.plantoryprojectbe.auth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zero.plantoryprojectbe.global.dto.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AuthUserResponse {
    private Long memberId;
    private String membername;
    private String nickname;
    private String phone;
    private String address;
    private Integer sharingRate;
    private Integer skillRate;
    private Integer managementRate;
    private Role role;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime stopDay;
}

