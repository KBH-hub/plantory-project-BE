package com.zero.plantoryprojectbe.profile;

import com.zero.plantoryprojectbe.global.security.MemberDetail;
import com.zero.plantoryprojectbe.profile.dto.ProfileSharingHistoryListRequest;
import com.zero.plantoryprojectbe.profile.dto.ProfileSharingHistoryListResponse;
import com.zero.plantoryprojectbe.profile.service.ProfileSharingHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/profileSharing")
public class ProfileSharingHistoryRestController {

    private final ProfileSharingHistoryService profileSharingHistoryService;

    @GetMapping("/my")
    public List<ProfileSharingHistoryListResponse> getMySharing(
            @AuthenticationPrincipal MemberDetail user,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status,
            @RequestParam int offset,
            @RequestParam int limit
    ) {
        ProfileSharingHistoryListRequest request = ProfileSharingHistoryListRequest.builder()
                .memberId(user.memberResponse().getMemberId())
                .keyword(keyword)
                .status(status)
                .offset(offset)
                .limit(limit)
                .build();

        return profileSharingHistoryService.getMySharingList(request);
    }

    @GetMapping("/received")
    public List<ProfileSharingHistoryListResponse> getReceivedSharing(
            @AuthenticationPrincipal MemberDetail memberDetail,
            @RequestParam(required = false) String keyword,
            @RequestParam String status,
            @RequestParam int offset,
            @RequestParam int limit
    ) {
        ProfileSharingHistoryListRequest request = ProfileSharingHistoryListRequest.builder()
                .memberId(memberDetail.memberResponse().getMemberId())
                .keyword(keyword)
                .status(status)
                .offset(offset)
                .limit(limit)
                .build();

        return profileSharingHistoryService.getReceivedSharingList(request);
    }

    @GetMapping("/counts")
    public Map<String, Integer> getProfileCounts(@AuthenticationPrincipal MemberDetail user) {
        Long memberId = user.memberResponse().getMemberId();

        int interest = profileSharingHistoryService.getInterestCount(memberId);
        int sharing = profileSharingHistoryService.getCompletedSharingCount(memberId);

        return Map.of(
                "interestCount", interest,
                "sharingCount", sharing
        );
    }

}
