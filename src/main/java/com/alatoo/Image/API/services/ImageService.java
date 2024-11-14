package com.alatoo.Image.API.services;

import com.alatoo.Image.API.dtos.AlbumDTO;
import com.alatoo.Image.API.dtos.ImageDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ImageService {
    List<ImageDTO> findAllImages();

    Optional<ImageDTO> findImageByID(UUID id);

    ImageDTO saveImage(ImageDTO dto);

    void deleteImage(UUID id);

    public List<ImageDTO> findImagesByUserId(UUID userId);

    public List<ImageDTO> findImagesByUserIdAndByAlbumId(UUID userId, UUID albumId);

    public List<ImageDTO> findImagesByAlbumId(UUID albumId);
}
