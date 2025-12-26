package com.zero.plantoryprojectbe.member;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class MemberImageRepositoryTest {
        @Autowired
        MemberImageRepository memberImageRepository;

        @Test
        void findTop1ByMemberId_latest_not_deleted() {
            Long memberId = 1L;

            var result = memberImageRepository
                    .findTop1ByMember_MemberIdAndDelFlagIsNullOrderByCreatedAtDesc(memberId);

            System.out.println("result present? " + result.isPresent());
            result.ifPresent(img -> {
                System.out.println("imageId=" + img.getImageId());
                System.out.println("fileUrl=" + img.getFileUrl());
                System.out.println("createdAt=" + img.getCreatedAt());
                System.out.println("delFlag=" + img.getDelFlag());
            });
        }
}