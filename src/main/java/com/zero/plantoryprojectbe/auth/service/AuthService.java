package com.zero.plantoryprojectbe.auth.service;


import com.zero.plantoryprojectbe.profile.dto.MemberResponse;
import com.zero.plantoryprojectbe.profile.dto.MemberSignUpRequest;

public interface AuthService {
    boolean isDuplicateMembername(String membername);
    boolean isDuplicateNickname(String nickname);
    void signUp(MemberSignUpRequest request);
    MemberResponse findById(Long userId);
}
