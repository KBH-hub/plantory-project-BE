package com.zero.plantory.global.security.jwt.service;

import com.zero.plantory.global.security.jwt.RefreshToken;
import com.zero.plantory.global.security.jwt.TokenProvider;
import com.zero.plantory.global.security.jwt.mapper.RefreshTokenMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenMapper refreshTokenMapper;
    private final TokenProvider tokenProvider;

    public RefreshToken findByRefreshToken(String refreshToken) {
        RefreshToken token = refreshTokenMapper.selectByRefreshToken(refreshToken);

        if (token == null) {
            throw new IllegalArgumentException("Unexpected refresh token");
        }

        return token;
    }

    @Transactional
    public void delete(String accessToken) {
//        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        Long memberId = tokenProvider.getMemberId(accessToken);
        refreshTokenMapper.deleteByMemberId(memberId);
    }

    @Transactional
    public void deleteByUserId(Long memberId) {
        refreshTokenMapper.deleteByMemberId(memberId);
    }

    @Transactional
    public void save(Long memberId, String refreshToken) {
        RefreshToken existing =
                refreshTokenMapper.selectByMemberId(memberId);

        if (existing == null) {
            RefreshToken token = new RefreshToken();
            token.setMemberId(memberId);
            token.setRefreshToken(refreshToken);

            refreshTokenMapper.insertRefreshToken(token);
        } else {
            existing.setRefreshToken(refreshToken);
            refreshTokenMapper.updateRefreshToken(existing);
        }
    }

}
