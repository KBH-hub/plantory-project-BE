package com.zero.plantoryprojectbe.weightManagement.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WeightLoggingResponse {
    private BigDecimal searchWeight;
    private BigDecimal questionWeight;
}
