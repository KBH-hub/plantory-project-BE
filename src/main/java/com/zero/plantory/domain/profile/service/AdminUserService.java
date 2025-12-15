package com.zero.plantory.domain.profile.service;

import com.zero.plantory.domain.member.mapper.MemberMapper;
import com.zero.plantory.domain.profile.dto.MemberResponse;
import com.zero.plantory.domain.profile.mapper.ProfileMapper;
import com.zero.plantory.global.security.MemberDetail;
import com.zero.plantory.global.security.jwt.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final RefreshTokenService refreshTokenService;

    public void forceLogout(Long memberId) {
        refreshTokenService.deleteByUserId(memberId);
    }
}

