package com.zero.plantoryprojectbe.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByMembernameAndDelFlagIsNull(String membername);
    boolean existsByNicknameAndDelFlagIsNull(String nickname);
    Optional<Member> findByMembernameAndDelFlagIsNull(String username);
    Optional<Member> findByMemberIdAndDelFlagIsNull(Long memberId);


}
