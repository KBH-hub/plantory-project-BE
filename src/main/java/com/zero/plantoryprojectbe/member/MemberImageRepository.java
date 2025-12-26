package com.zero.plantoryprojectbe.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberImageRepository extends JpaRepository<MemberImage, Long> {
    Optional<MemberImage> findTop1ByMember_MemberIdAndDelFlagIsNullOrderByCreatedAtDesc(Long memberId);
}
