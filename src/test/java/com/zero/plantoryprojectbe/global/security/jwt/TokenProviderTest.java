package com.zero.plantoryprojectbe.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private JwtProperties jwtProperties;

    @Test
    @DisplayName("createAccessToken(): memberId로 토큰을 생성하고 검증에 성공한다")
    void createAccessToken_success() {
        // given
        String memberId = "1";

        // when
        String token = tokenProvider.createAccessToken(memberId);

        // then
        assertThat(token).isNotNull();
        assertThat(tokenProvider.validateToken(token)).isTrue();
    }



    @Test
    @DisplayName("validateToken(): 만료된 토큰은 false를 반환한다")
    void validateToken_expired() {
        // given
        Key key = Keys.hmacShaKeyFor(
                jwtProperties.getSecret().getBytes()
        );

        String expiredToken = Jwts.builder()
                .setSubject("1")
                .setExpiration(new Date(System.currentTimeMillis() - 1000))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // when
        boolean result = tokenProvider.validateToken(expiredToken);

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("getAuthentication(): 토큰으로 Authentication 객체를 생성한다")
    void getAuthentication() {
        // given
        String memberId = "1";
        String token = tokenProvider.createAccessToken(memberId);

        // when
        Authentication authentication = tokenProvider.getAuthentication(token);

        // then
        assertThat(authentication).isNotNull();
        assertThat(authentication.isAuthenticated()).isTrue();
        assertThat(authentication.getPrincipal()).isInstanceOf(UserDetails.class);
        assertThat(((UserDetails) authentication.getPrincipal()).getUsername())
                .isEqualTo(memberId);
    }



    @Test
    @DisplayName("createAccessToken(): subject에 memberId가 들어간다")
    void createAccessToken_subject() {
        // given
        String memberId = "1";

        // when
        String token = tokenProvider.createAccessToken(memberId);

        // then
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertThat(claims.getSubject()).isEqualTo(memberId);
    }



}