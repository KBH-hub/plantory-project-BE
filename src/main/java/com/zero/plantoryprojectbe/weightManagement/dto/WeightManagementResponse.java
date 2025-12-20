package com.zero.plantoryprojectbe.weightManagement.dto;

import lombok.Data;

@Data
public class WeightManagementResponse {
    private Long memberId;
    private String membername;
    private String nickname;
    private Integer searchWeight;
    private Integer questionWeight;
    private Integer plantsNeedingAttention;
    private Long totalCount;
}
