package com.zero.plantoryprojectbe.image;

import com.zero.plantoryprojectbe.image.dto.ImageDTO;
import com.zero.plantoryprojectbe.global.plantoryEnum.ImageTargetType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ImageMapper {

    String selectSharingThumbnail(@Param("targetType") ImageTargetType targetType, @Param("targetId") Long targetId);

    ImageDTO selectImageById(@Param("imageId") Long imageId);
    List<ImageDTO> selectImagesByTarget(@Param("targetType") ImageTargetType targetType, @Param("targetId") Long targetId);

    int insertImage(ImageDTO imageDTO);
    int softDeleteImage(@Param("imageId") Long imageId);
    int softDeleteImagesByTarget(@Param("targetType") ImageTargetType targetType, @Param("targetId") Long targetId);

    ImageDTO selectLatestProfileImage(@Param("targetType") ImageTargetType targetType,
                                      @Param("memberId") Long memberId);

    String getProfileImageUrl(Long memberId);

}
