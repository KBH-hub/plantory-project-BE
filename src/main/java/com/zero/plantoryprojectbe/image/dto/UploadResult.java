package com.zero.plantoryprojectbe.image.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadResult {
    private String url;
    private String fileName;
}

