package com.zero.plantoryprojectbe.profile;

import com.zero.plantoryprojectbe.global.security.MemberDetail;
import com.zero.plantoryprojectbe.image.service.ImageService;
import com.zero.plantoryprojectbe.profile.dto.PasswordChangeRequest;
import com.zero.plantoryprojectbe.profile.dto.ProfileInfoResponse;
import com.zero.plantoryprojectbe.profile.dto.ProfileUpdateRequest;
import com.zero.plantoryprojectbe.profile.dto.PublicProfileResponse;
import com.zero.plantoryprojectbe.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileRestController {
    private final ProfileService profileService;
    private final ImageService imageService;

    @GetMapping("/me")
    public ResponseEntity<ProfileInfoResponse> getProfile(@AuthenticationPrincipal MemberDetail memberDetail) {
        return ResponseEntity.ok(profileService.getProfileInfo(memberDetail.memberResponse().getMemberId()));
    }

    @GetMapping("/publicProfile/{memberId}")
    public ResponseEntity<PublicProfileResponse> getPublicProfile(@PathVariable Long memberId) {
        return ResponseEntity.ok(profileService.getPublicProfile(memberId));
    }

    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(
            @AuthenticationPrincipal MemberDetail memberDetail,
            @RequestBody PasswordChangeRequest req) {

//        log.info("비밀번호 변경 객체로그"+req.toString());

        boolean success = profileService.changePassword(
                memberDetail.memberResponse().getMemberId(),
                req.getOldPassword(),
                req.getNewPassword()
        );

        return ResponseEntity.ok(Map.of("success", success));
    }

    @PutMapping("/withdraw")
    public ResponseEntity<?> softWithdraw(
            @AuthenticationPrincipal MemberDetail memberDetail) {

        Long memberId = memberDetail.memberResponse().getMemberId();
        profileService.deleteMemberById(memberId);

        return ResponseEntity.ok("회원 탈퇴 완료");
    }

    @PutMapping
    public ResponseEntity<?> updateProfile(
            @AuthenticationPrincipal MemberDetail memberDetail,
            @RequestBody ProfileUpdateRequest profileUpdateRequest) {

        Long memberId = memberDetail.memberResponse().getMemberId();
        profileUpdateRequest.setMemberId(memberId);

        profileService.updateProfileInfo(profileUpdateRequest);

        return ResponseEntity.ok("success");
    }

    @PostMapping("/picture")
    public Map<String, Object> uploadProfile(@AuthenticationPrincipal MemberDetail memberDetail ,@RequestParam("profileImage") MultipartFile file) throws IOException {
        Long memberId = memberDetail.memberResponse().getMemberId();
        String imageUrl = imageService.uploadProfileImage(memberId, file);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("imageUrl", imageUrl);

        return response;
    }

    @GetMapping("/picture")
    public Map<String, Object> getProfileImage(@RequestParam Long memberId) {

        String imageUrl = imageService.getProfileImageUrl(memberId);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("imageUrl", imageUrl);

        return response;
    }




}
