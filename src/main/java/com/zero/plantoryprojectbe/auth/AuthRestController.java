package com.zero.plantoryprojectbe.auth;

import com.zero.plantoryprojectbe.auth.service.AuthService;
import com.zero.plantoryprojectbe.profile.dto.MemberSignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class AuthRestController {

    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(
            @RequestBody MemberSignUpRequest request
    ) {
        authService.signUp(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/checkMembername")
    public ResponseEntity<Map<String, Boolean>> checkMembername(
            @RequestParam String membername) {

        boolean isDuplicate = authService.isDuplicateMembername(membername);
        return ResponseEntity.ok(Map.of("exists", isDuplicate));
    }

    @GetMapping("/checkNickname")
    public ResponseEntity<Map<String, Boolean>> checkNickname(
            @RequestParam String nickname) {

        boolean isDuplicate = authService.isDuplicateNickname(nickname);
        return ResponseEntity.ok(Map.of("exists", isDuplicate));
    }
}