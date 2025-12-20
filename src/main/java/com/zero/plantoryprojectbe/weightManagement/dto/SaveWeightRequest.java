package com.zero.plantoryprojectbe.weightManagement.dto;

import lombok.Data;

@Data
public class SaveWeightRequest {
    private Long memberId;
    private Double searchWeight;
    private Double questionWeight;
}

