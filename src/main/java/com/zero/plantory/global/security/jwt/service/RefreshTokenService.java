package com.zero.plantory.global.security.jwt.service;

import com.zero.plantory.global.security.jwt.RefreshToken;
import com.zero.plantory.global.security.jwt.mapper.RefreshTokenMapper;
import com.zero.plantory.global.utils.TokenHashUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenMapper refreshTokenMapper;
    private final TokenHashUtil tokenHashUtil;

    public RefreshToken findByRefreshToken(String refreshToken) {
        String tokenHash = tokenHashUtil.hash(refreshToken);

        RefreshToken token =
                refreshTokenMapper.selectByTokenHash(tokenHash);

        if (token == null) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        return token;
    }

    @Transactional
    public void deleteByToken(String refreshToken) {
        String tokenHash = tokenHashUtil.hash(refreshToken);
        refreshTokenMapper.deleteByTokenHash(tokenHash);
    }

    @Transactional
    public void deleteByUserId(Long memberId) {
        refreshTokenMapper.deleteByMemberId(memberId);
    }

    @Transactional
    public void save(Long memberId, String refreshToken) {
        String tokenHash = tokenHashUtil.hash(refreshToken);

        RefreshToken existing =
                refreshTokenMapper.selectByMemberId(memberId);

        if (existing == null) {
            RefreshToken token = new RefreshToken();
            token.setMemberId(memberId);
            token.setTokenHash(tokenHash);

            refreshTokenMapper.insertRefreshToken(token);
        } else {
            existing.setTokenHash(tokenHash);
            refreshTokenMapper.updateRefreshToken(existing);
        }
    }
}

