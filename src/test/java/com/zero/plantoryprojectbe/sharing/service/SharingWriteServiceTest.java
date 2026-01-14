package com.zero.plantoryprojectbe.sharing.service;

import com.zero.plantoryprojectbe.global.plantoryEnum.ManageDemand;
import com.zero.plantoryprojectbe.global.plantoryEnum.ManageLevel;
import com.zero.plantoryprojectbe.sharing.dto.CommentRequest;
import com.zero.plantoryprojectbe.sharing.dto.SharingRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SharingWriteServiceTest {

    @Autowired
    private SharingWriteService sharingWriteService;

    @Test
    @Order(1)
    @DisplayName("나눔글 + 이미지 등록")
    void registerSharingTest() throws Exception {

        MockMultipartFile file1 = new MockMultipartFile(
                "file",
                "img1.png",
                "image/png",
                "image1".getBytes()
        );

        MockMultipartFile file2 = new MockMultipartFile(
                "file",
                "img2.png",
                "image/png",
                "image2".getBytes()
        );

        List<MultipartFile> fileList = List.of(file1, file2);

        SharingRequest request = SharingRequest.builder()
                .memberId(1L)
                .title("테스트 글")
                .content("테스트 내용")
                .plantType("다육이")
                .managementLevel(ManageLevel.EASY)
                .managementNeeds(ManageDemand.LITTLE_CARE)
                .build();

        Long id = sharingWriteService.registerSharing(request, fileList);
        log.info("등록된 글 ID = {}", id);
    }

    @Test
    @Order(2)
    @DisplayName("나눔글 수정")
    void updateSharingTest() throws Exception {

        MockMultipartFile newMockFile = new MockMultipartFile(
                "file",
                "updated.png",
                "image/png",
                "updated image".getBytes()
        );

        SharingRequest request = SharingRequest.builder()
                .sharingId(3L)
                .memberId(1L)
                .title("수정된 제목")
                .content("수정된 내용")
                .plantType("다육이")
                .managementLevel(ManageLevel.EASY)
                .managementNeeds(ManageDemand.LITTLE_CARE)
                .build();

        boolean result = sharingWriteService.updateSharing(request, List.of(newMockFile));
        log.info("수정 결과 = {}", result);
    }

    @Test
    @Order(3)
    @DisplayName("나눔글 삭제")
    void deleteSharingTest() {
        log.info("삭제 결과 = {}", sharingWriteService.deleteSharing(3L, 1L));
    }

    @Test
    @Order(4)
    @DisplayName("관심 등록")
    void addInterestTest() {
        log.info("관심 등록 결과 = {}", sharingWriteService.addInterest(1L, 1L));
    }

    @Test
    @Order(5)
    @DisplayName("관심 해제")
    void removeInterestTest() {
        log.info("관심 해제 결과 = {}", sharingWriteService.removeInterest(3L, 12L));
    }

    @Test
    @Order(6)
    @DisplayName("댓글 등록")
    void addCommentTest() {
        CommentRequest request = CommentRequest.builder()
                .commentId(1L)
                .sharingId(3L)
                .writerId(2L)
                .content("댓글")
                .build();
        log.info("댓글 등록 결과 = {}", sharingWriteService.addComment(request));
    }

    @Test
    @Order(7)
    @DisplayName("댓글 수정")
    void updateCommentTest() {
        CommentRequest request = CommentRequest.builder()
                .commentId(1L)
                .sharingId(1L)
                .writerId(2L)
                .content("수정된 댓글")
                .build();
        log.info("댓글 수정 결과 = {}", sharingWriteService.updateComment(request));
    }

    @Test
    @Order(8)
    @DisplayName("댓글 삭제")
    void deleteCommentTest() {
        log.info("댓글 삭제 결과 = {}", sharingWriteService.deleteComment(1L, 3L, 2L));
    }
}
