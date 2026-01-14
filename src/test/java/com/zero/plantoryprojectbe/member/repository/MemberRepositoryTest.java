package com.zero.plantoryprojectbe.member.repository;

import com.zero.plantoryprojectbe.member.Member;
import com.zero.plantoryprojectbe.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입 처리 (JPA save)")
    void saveMemberTest() {
        Member member = Member.createForSignUp(
                "testUser",
                "pw1234!",
                "테스트유저",
                "010-1234-5678",
                "서울특별시 영등포구"
        );

        Member saved = memberRepository.save(member);

        assertNotNull(saved.getMemberId());
        assertTrue(memberRepository.findByMemberIdAndDelFlagIsNull(saved.getMemberId()).isPresent());
    }

    @Test
    @DisplayName("아이디 중복 확인 (existsByMembernameAndDelFlagIsNull)")
    void existsByMembernameTest() {
        Member member = Member.createForSignUp(
                "honggd01",
                "pw1234!",
                "아무닉",
                "010-0000-0000",
                "서울"
        );
        memberRepository.save(member);

        boolean exists = memberRepository.existsByMembernameAndDelFlagIsNull("honggd01");

        assertTrue(exists);
    }

    @Test
    @DisplayName("닉네임 중복 확인 (existsByNicknameAndDelFlagIsNull)")
    void existsByNicknameTest() {
        Member member = Member.createForSignUp(
                "someUser",
                "pw1234!",
                "길동가든",
                "010-0000-0000",
                "서울"
        );
        memberRepository.save(member);

        boolean exists = memberRepository.existsByNicknameAndDelFlagIsNull("길동가든");

        assertTrue(exists);
    }
}
