package com.zero.plantoryprojectbe.global.utils;

import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
@Primary
@RequiredArgsConstructor
public class S3Uploader implements StorageUploader {

    private final S3Template s3Template;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${spring.cloud.aws.region.static}")
    private String region;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        try {
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            String fileName = String.format("images/%s/%s-%s",
                    today,
                    UUID.randomUUID(),
                    file.getOriginalFilename()
            );

            s3Template.upload(bucket, fileName, file.getInputStream());
            return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, region, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    public boolean deleteFile(String fileUrl) {
        try {
            String key = fileUrl.substring(fileUrl.lastIndexOf("images/"));
            s3Template.deleteObject(bucket, key);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
