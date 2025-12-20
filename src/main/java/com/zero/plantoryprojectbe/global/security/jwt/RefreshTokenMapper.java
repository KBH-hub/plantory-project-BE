package com.zero.plantoryprojectbe.global.security.jwt;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefreshTokenMapper {

    int insertRefreshToken(RefreshToken refreshToken);
    int updateRefreshToken(RefreshToken refreshToken);
    int deleteByMemberId(Long memberId);
    RefreshToken selectByMemberId(Long memberId);
    RefreshToken selectByTokenHash(String tokenHash);
    int deleteByTokenHash(String tokenHash);
}

