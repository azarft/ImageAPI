package com.alatoo.Image.API.services.Impl;

import com.alatoo.Image.API.dtos.ImageDTO;
import com.alatoo.Image.API.entities.AlbumEntity;
import com.alatoo.Image.API.entities.ImageEntity;
import com.alatoo.Image.API.entities.UserEntity;
import com.alatoo.Image.API.exceptions.NotFoundException;
import com.alatoo.Image.API.file.ImageFileManager;
import com.alatoo.Image.API.mappers.ImageMapper;
import com.alatoo.Image.API.repositories.ImageRepository;
import com.alatoo.Image.API.services.AlbumService;
import com.alatoo.Image.API.services.ImageService;
import com.alatoo.Image.API.services.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ImageServiceJpa implements ImageService {
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;
    private final AlbumService albumService;
    private final UserService userService;
    private final ImageFileManager imageFileManager;

    public ImageServiceJpa(ImageRepository imageRepository, ImageMapper imageMapper, AlbumService albumService, UserService userService, ImageFileManager imageFileManager) {
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
        this.albumService = albumService;
        this.userService = userService;
        this.imageFileManager = imageFileManager;
    }

    @Override
    public List<ImageDTO> findAllImages() {
        UserEntity user = userService.getCurrentUser();
        List<ImageEntity> images = imageRepository.getImageEntitiesByUserId(user.getId());
        return images.stream()
                .map(imageMapper::imageEntityToImageDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ImageDTO> findImageByID(UUID id) {
        UserEntity user = userService.getCurrentUser();
        Optional<ImageEntity> optionalImageEntity = imageRepository.findByImageIdAndUserId(id, user.getId());
        ImageEntity image = optionalImageEntity.orElseThrow(() -> new NotFoundException("Image not found with id: " + id));
        return Optional.ofNullable(imageMapper.imageEntityToImageDto(image));
    }

    @Override
    public ImageDTO saveImage(ImageDTO imageDTO) {
        UserEntity currentUser = userService.getCurrentUser();

        ImageEntity image = imageMapper.imageDtoToImageEntity(imageDTO);
        if (imageDTO.getAlbumId() == null) {
            AlbumEntity defaultAlbum = albumService.getDefaultAlbum(currentUser);
            image.setAlbum(defaultAlbum);
        }
        image.setUser(currentUser);
        image.setUploadedAt(LocalDateTime.now());
        ImageEntity savedImage = imageRepository.save(image);


        try {
            imageFileManager.saveImageFile(image.getImageFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageMapper.imageEntityToImageDto(savedImage);
    }

    @Override
    public void deleteImage(UUID id) {
        UserEntity user = userService.getCurrentUser();
        if (!imageRepository.existsByImageIdAndUserId(id, user.getId())) {
            throw new NotFoundException("Image not found with id: " + id);
        }
        imageRepository.deleteById(id);
    }

    @Override
    public List<ImageDTO> findImagesByUserId(UUID userId) {
        List<ImageEntity> images = imageRepository.getImageEntitiesByUserId(userId);
        return images.stream()
                .map(imageMapper::imageEntityToImageDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ImageDTO> findImagesByAlbumId(UUID albumId) {
        List<ImageEntity> images = imageRepository.getImageEntitiesByAlbumAlbumId(albumId);
        return images.stream()
                .map(imageMapper::imageEntityToImageDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ImageDTO> findImagesByUserIdAndByAlbumId(UUID userId, UUID albumId) {
        List<ImageEntity> images = imageRepository.getImageEntitiesByUserIdAndAlbumAlbumId(userId, albumId);
        return images.stream()
                .map(imageMapper::imageEntityToImageDto)
                .collect(Collectors.toList());
    }

}
