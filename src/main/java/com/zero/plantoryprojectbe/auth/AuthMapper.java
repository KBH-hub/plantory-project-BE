package com.zero.plantoryprojectbe.auth;

import com.zero.plantoryprojectbe.profile.dto.MemberResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthMapper {
    int countByMembername(@Param("membername") String membername);
    int countByNickname(@Param("nickname") String nickname);
    int insertMember(MemberResponse memberResponse);
    MemberResponse selectByMembername(@Param("membername") String membername);
    MemberResponse selectByMemberId(@Param("memberId") Long memberId);
    void resetStopDay(Long memberId);
}

