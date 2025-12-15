package com.zero.plantory.domain.member.controller;

import com.zero.plantory.domain.member.service.MemberService;
import com.zero.plantory.domain.profile.dto.MemberSignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberRestController {

    private final MemberService memberService;

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(
            @RequestBody MemberSignUpRequest request
    ) {
        memberService.signUp(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/checkMembername")
    public ResponseEntity<Map<String, Boolean>> checkMembername(
            @RequestParam String membername) {

        boolean isDuplicate = memberService.isDuplicateMembername(membername);
        return ResponseEntity.ok(Map.of("exists", isDuplicate));
    }

    @GetMapping("/checkNickname")
    public ResponseEntity<Map<String, Boolean>> checkNickname(
            @RequestParam String nickname) {

        boolean isDuplicate = memberService.isDuplicateNickname(nickname);
        return ResponseEntity.ok(Map.of("exists", isDuplicate));
    }
}