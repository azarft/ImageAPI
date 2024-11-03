package com.alatoo.Image.API.mappers;

import com.alatoo.Image.API.dtos.ImageDTO;
import com.alatoo.Image.API.entities.ImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.awt.*;

@Mapper
public interface ImageMapper {
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "albumId", target = "album.albumId")
    ImageEntity imageDtoToImageEntity(ImageDTO dto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "album.albumId", target = "albumId")
    ImageDTO imageEntityToImageDto(ImageEntity image);
}
