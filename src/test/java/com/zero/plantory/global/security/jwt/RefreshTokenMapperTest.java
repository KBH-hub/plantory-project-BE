package com.zero.plantory.global.security.jwt;

import com.zero.plantory.global.security.jwt.mapper.RefreshTokenMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@SpringBootTest
@Transactional
class RefreshTokenMapperTest {


    @Autowired
    RefreshTokenMapper refreshTokenMapper;


    @Test
    void insertRefreshToken() {
        // given
        RefreshToken token =  new RefreshToken();
        token.setMemberId(1L);
        token.setTokenHash("insert-test-token");

        // when
        int result = refreshTokenMapper.insertRefreshToken(token);
        log.info("result로그결과:{}", result);
        // then
        assertThat(result).isEqualTo(1);
        assertThat(token.getRefreshTokenId()).isNotNull();
    }

    @Test
    void selectByMemberId() {
        // given
        RefreshToken token = new RefreshToken();
        token.setMemberId(2L);
        token.setTokenHash("select-member-token");
        refreshTokenMapper.insertRefreshToken(token);

        // when
        RefreshToken found =
                refreshTokenMapper.selectByMemberId(2L);

        // then
        assertThat(found).isNotNull();
        assertThat(found.getMemberId()).isEqualTo(2L);
        assertThat(found.getTokenHash()).isEqualTo("select-member-token");
    }

    @Test
    void selectByTokenHash() {
        // given
        RefreshToken token = new RefreshToken();
        token.setMemberId(3L);
        token.setTokenHash("select-refresh-token");
        refreshTokenMapper.insertRefreshToken(token);

        // when
        RefreshToken found = refreshTokenMapper.selectByTokenHash("select-refresh-token");

        // then
        assertThat(found).isNotNull();
        assertThat(found.getMemberId()).isEqualTo(3L);
        assertThat(found.getTokenHash()).isEqualTo("select-refresh-token");
    }

    @Test
    void updateRefreshToken() {
        // given
        RefreshToken token = new RefreshToken();
        token.setMemberId(4L);
        token.setTokenHash("old-token");
        refreshTokenMapper.insertRefreshToken(token);

        // when
        token.setTokenHash("new-token");
        int updated = refreshTokenMapper.updateRefreshToken(token);

        RefreshToken updatedToken = refreshTokenMapper.selectByMemberId(4L);

        // then
        assertThat(updated).isEqualTo(1);
        assertThat(updatedToken.getTokenHash()).isEqualTo("new-token");
    }

    @Test
    void deleteByMemberId() {
        //given
        RefreshToken token = new RefreshToken();
        token.setMemberId(5L);
        token.setTokenHash("delete-token");
        refreshTokenMapper.insertRefreshToken(token);

        //when
        int deleted = refreshTokenMapper.deleteByMemberId(5L);

        RefreshToken found = refreshTokenMapper.selectByMemberId(5L);

        //then
        assertThat(deleted).isEqualTo(1);
        assertThat(found).isNull();
    }
}