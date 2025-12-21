package com.zero.plantoryprojectbe.sharing;

import com.zero.plantoryprojectbe.global.security.MemberDetail;
import com.zero.plantoryprojectbe.sharing.dto.*;
import com.zero.plantoryprojectbe.sharing.service.SharingReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sharings")
@RequiredArgsConstructor
public class SharingReadRestController {

    private final SharingReadService sharingReadService;


    @GetMapping
    public List<SharingCardListResponse> getSharingList(SharingSearchRequest request) {
        return sharingReadService.getSharingList(request);
    }

    @GetMapping("/countInterest")
    public int countInterest(@AuthenticationPrincipal MemberDetail memberDetail) {
        return sharingReadService.countInterest(memberDetail.memberResponse().getMemberId());
    }

    @GetMapping("/popular")
    public List<SharingPopularResponse> getPopularSharingList(SharingSearchRequest request) {
        return sharingReadService.getPopularSharingList(request);
    }


    @GetMapping("/{sharingId}")
    public SelectSharingDetailResponse getSharingDetail(@PathVariable Long sharingId,  @AuthenticationPrincipal MemberDetail memberDetail) {
        Long memberId = (memberDetail != null) ? memberDetail.memberResponse().getMemberId() : null;
        return sharingReadService.getSharingDetail(sharingId, memberId);
    }

    @GetMapping("/{sharingId}/comments")
    public List<SelectCommentListResponse> getSharingComments(@PathVariable Long sharingId) {
        return sharingReadService.getSharingComments(sharingId);
    }

    @GetMapping("/{sharingId}/partners")
    public List<SharingPartnerResponse> getMessagePartners(
            @PathVariable Long sharingId,
            @AuthenticationPrincipal MemberDetail memberDetail
    ) {
        Long receiverId = memberDetail.memberResponse().getMemberId();
        return sharingReadService.getMessagePartners(receiverId, sharingId);
    }

    @GetMapping("/history/given")
    public List<SharingHistoryResponse> getMySharingGiven(@RequestParam Long memberId) {
        return sharingReadService.getMySharingGiven(memberId);
    }

    @GetMapping("/history/received")
    public List<SharingHistoryResponse> getMySharingReceived(@RequestParam Long memberId) {
        return sharingReadService.getMySharingReceived(memberId);
    }

    @GetMapping("/{sharingId}/reviewInfo")
    public ResponseEntity<ReviewInfoResponse> getReviewInfo(
            @PathVariable Long sharingId,
            @AuthenticationPrincipal MemberDetail memberDetail
    ) {
        Long memberId = memberDetail.memberResponse().getMemberId();
        ReviewInfoResponse result = sharingReadService.getReviewInfo(sharingId, memberId);

        return ResponseEntity.ok(result);
    }


}

