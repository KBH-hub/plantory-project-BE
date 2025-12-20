package com.zero.plantoryprojectbe.auth.service;

import com.zero.plantoryprojectbe.auth.AuthMapper;
import com.zero.plantoryprojectbe.global.dto.Role;
import com.zero.plantoryprojectbe.profile.dto.MemberResponse;
import com.zero.plantoryprojectbe.profile.dto.MemberSignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthMapper authMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean isDuplicateMembername(String membername) {
        return authMapper.countByMembername(membername) > 0;
    }

    @Override
    public boolean isDuplicateNickname(String nickname) {
        return authMapper.countByNickname(nickname) > 0;
    }

    @Override
    @Transactional
    public void signUp(MemberSignUpRequest request) {

//        log.info(String.valueOf(request));

        if (authMapper.countByMembername(request.getMembername()) > 0) {
            throw new IllegalStateException("이미 사용 중인 아이디입니다.");
        }
        if (authMapper.countByNickname(request.getNickname()) > 0) {
            throw new IllegalStateException("이미 사용 중인 닉네임입니다.");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());

//        log.info(String.valueOf(request));

        MemberResponse memberResponse = MemberResponse.builder()
                .membername(request.getMembername())
                .password(encodedPassword)
                .nickname(request.getNickname())
                .phone(request.getPhone())
                .address(request.getAddress())
                .sharingRate(7)
                .skillRate(0)
                .managementRate(0)
                .role(Role.USER)
                .noticeEnabled(1)
                .build();

                 authMapper.insertMember(memberResponse);
    }

    @Override
    public MemberResponse findById(Long memberId) {
        return authMapper.selectByMemberId(memberId);
    }

//    @Override
//    public MemberResponse login(String membername, String password) {
//        MemberResponse memberResponse = AuthMapper.selectByMembername(membername);
//
//        if (memberResponse == null) {
//            return null;
//        }
//
//        if (memberResponse.getStopDay() != null) {
//
//            LocalDateTime now = LocalDateTime.now();
//            LocalDateTime stopDay = memberResponse.getStopDay();
//
//            if (stopDay.isAfter(now)) {
//                long days = ChronoUnit.DAYS.between(now, stopDay); //Chrono시간 + Unit 단위
//                throw new IllegalStateException(String.format("정지 해제까지 %d일 남았습니다.", days));
//            } else {
//                AuthMapper.resetStopDay(memberResponse.getMemberId());
//                memberResponse.setStopDay(null);
//            }
//        }
//
//        if (!bCryptPasswordEncoder.matches(password, memberResponse.getPassword())) {
//            return null;
//        }
//
//        return memberResponse;
//    }


}
