package com.zero.plantoryprojectbe.auth.mapper;

import com.zero.plantoryprojectbe.auth.AuthMapper;
import com.zero.plantoryprojectbe.global.dto.Role;
import com.zero.plantoryprojectbe.profile.dto.MemberResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
class MemberDetailMapperTest {

    @Autowired
    AuthMapper authMapper;

    @Test
    @DisplayName("회원가입 처리")
    void insertMemberTest() {
        MemberResponse member = MemberResponse.builder()
                .membername("testUser")
                .password("pw1234!")
                .nickname("테스트유저")
                .phone("010-1234-5678")
                .address("서울특별시 영등포구")
                .role(Role.USER)
                .noticeEnabled(1)
                .build();

        int result = authMapper.insertMember(member);
        assertTrue(result > 0);
    }

    @Test
    @DisplayName("아이디 중복 확인")
    void countByMembernameTest() {
        int count = authMapper.countByMembername("honggd01");
        log.info("count = {}", count);
        assertTrue(count >= 0);
    }

    @Test
    @DisplayName("닉네임 중복 확인")
    void countByNicknameTest() {
        int count = authMapper.countByNickname("길동가든");
        assertTrue(count >= 0);
    }

}
