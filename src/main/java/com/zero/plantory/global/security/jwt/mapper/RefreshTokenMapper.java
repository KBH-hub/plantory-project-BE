package com.zero.plantory.global.security.jwt.mapper;

import com.zero.plantory.global.security.jwt.RefreshToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefreshTokenMapper {

    int insertRefreshToken(RefreshToken refreshToken);
    int updateRefreshToken(RefreshToken refreshToken);
    int deleteByMemberId(Long memberId);
    RefreshToken selectByMemberId(Long memberId);
    RefreshToken selectByRefreshToken(String refreshToken);

}

