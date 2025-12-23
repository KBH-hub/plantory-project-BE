package com.zero.plantoryprojectbe.question.dto;

import com.zero.plantoryprojectbe.global.dto.ImageDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SelectQuestionDetailResponse {
    private Long questionId;
    private Long memberId;
    private String nickname;
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;

    private String imageUrl;
    private List<ImageDTO> images;
}
