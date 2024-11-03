package com.alatoo.Image.API.mappers;

import com.alatoo.Image.API.dtos.AlbumDTO;
import com.alatoo.Image.API.entities.AlbumEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AlbumMapper {
    @Mapping(source = "userId", target = "user.id")
    AlbumEntity albumDtoToAlbumEntity(AlbumDTO dto);
    @Mapping(source = "user.id", target = "userId")
    AlbumDTO albumEntityToAlbumDto(AlbumEntity album);
}
