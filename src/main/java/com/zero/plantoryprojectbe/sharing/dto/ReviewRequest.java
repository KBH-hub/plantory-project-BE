package com.zero.plantoryprojectbe.sharing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
    private int manner;
    private int reShare;
    private Integer satisfaction;
}

