package com.zero.plantoryprojectbe.sharing;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zero.plantoryprojectbe.global.security.MemberDetail;
import com.zero.plantoryprojectbe.sharing.dto.CommentRequest;
import com.zero.plantoryprojectbe.sharing.dto.SharingRequest;
import com.zero.plantoryprojectbe.sharing.service.SharingWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/sharings")
@RequiredArgsConstructor
public class SharingWriteRestController {

    private final SharingWriteService sharingWriteService;
    private final SharingMapper sharingMapper;

    @PostMapping
    public ResponseEntity<?> createSharing(
            @AuthenticationPrincipal MemberDetail memberDetail,
            @ModelAttribute SharingRequest request,
            @RequestParam(value = "files", required = false) List<MultipartFile> files) throws IOException {
        request.setMemberId(memberDetail.memberResponse().getMemberId());
        Long sharingId = sharingWriteService.registerSharing(request, files);
        return ResponseEntity.ok(sharingId);
    }

    @PutMapping("/{sharingId}")
    public ResponseEntity<?> updateSharing(
            @PathVariable Long sharingId,
            @ModelAttribute SharingRequest request,
            @RequestParam(value = "files", required = false) List<MultipartFile> files) throws IOException {

        request.setSharingId(sharingId);

        if (request.getDeletedImageIds() != null && !request.getDeletedImageIds().isBlank()) {
            ObjectMapper mapper = new ObjectMapper();

            List<Long> ids = mapper.readValue(
                    request.getDeletedImageIds(),
                    new TypeReference<List<Long>>() {}
            );

            request.setDeletedImageIdList(ids);
        }

        boolean result = sharingWriteService.updateSharing(request, files);
        return ResponseEntity.ok(result);
    }


    @DeleteMapping("/{sharingId}")
    public ResponseEntity<?> deleteSharing(
            @PathVariable Long sharingId,
            @AuthenticationPrincipal MemberDetail memberDetail) throws IOException {
        Long memberId = memberDetail.memberResponse().getMemberId();
        boolean result = sharingWriteService.deleteSharing(sharingId, memberId);
        return ResponseEntity.ok(result);
    }


    @PostMapping("/{sharingId}/interest")
    public ResponseEntity<?> addInterest(
            @PathVariable Long sharingId,
            @AuthenticationPrincipal MemberDetail memberDetail) throws IOException {
        Long memberId = memberDetail.memberResponse().getMemberId();
        boolean result = sharingWriteService.addInterest(memberId, sharingId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{sharingId}/interest")
    public ResponseEntity<?> removeInterest(
            @PathVariable Long sharingId,
            @AuthenticationPrincipal MemberDetail memberDetail) throws IOException {
        Long memberId = memberDetail.memberResponse().getMemberId();
        boolean result = sharingWriteService.removeInterest(memberId, sharingId);
        return ResponseEntity.ok(result);
    }


    @PostMapping("/{sharingId}/comments")
    public ResponseEntity<?> addComment(
            @PathVariable Long sharingId,
            @AuthenticationPrincipal MemberDetail memberDetail,
            @RequestBody CommentRequest request) {

        Long loginMemberId = memberDetail.memberResponse().getMemberId();
        request.setWriterId(loginMemberId);
        request.setSharingId(sharingId);

        boolean result = sharingWriteService.addComment(request);
        return ResponseEntity.ok(result);
    }


    @PutMapping("/{sharingId}/comments/{commentId}")
    public ResponseEntity<?> updateComment(
            @PathVariable Long sharingId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal MemberDetail memberDetail,
            @RequestBody CommentRequest request
    ) {
        request.setCommentId(commentId);
        request.setWriterId(memberDetail.memberResponse().getMemberId());
        request.setSharingId(sharingId);

        boolean result = sharingWriteService.updateComment(request);
        return ResponseEntity.ok(result);
    }


    @DeleteMapping("/{sharingId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable Long commentId,
            @PathVariable Long sharingId,
            @AuthenticationPrincipal MemberDetail memberDetail
    ) {
        Long loginMemberId = memberDetail.memberResponse().getMemberId();


        boolean result = sharingWriteService.deleteComment(commentId, sharingId, loginMemberId);
        return ResponseEntity.ok(result);
    }


}
