package com.zero.plantory.global.security.jwt;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        String token = getAccessToken(authorizationHeader);

        if (token != null && tokenProvider.validateToken(token)) {
            Authentication authentication =
                    tokenProvider.getAuthentication(token);

            if (authentication != null) {
                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        } else {
            log.info("JWT 없음 또는 유효하지 않음: {}", request.getRequestURI());
        }


        filterChain.doFilter(request, response);
    }

    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null &&
                authorizationHeader.startsWith(TOKEN_PREFIX)) {

            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}

