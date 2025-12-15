package com.zero.plantory.global.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt")
public class JwtProperties {
    //application.yml에 설정한 :jwt 값들이 자동으로 바인딩돼서 들어옴.
    private String issuer;
    private String secret;
    private long accessTokenExpiration;
    private long refreshTokenExpiration;
}
