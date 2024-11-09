package com.alatoo.Image.API.services;

import com.alatoo.Image.API.dtos.AlbumDTO;
import com.alatoo.Image.API.dtos.ImageDTO;
import com.alatoo.Image.API.entities.ImageEntity;
import com.alatoo.Image.API.entities.UserEntity;
import com.alatoo.Image.API.exceptions.NotFoundException;
import com.alatoo.Image.API.mappers.ImageMapper;
import com.alatoo.Image.API.repositories.ImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ImageServiceJpa implements ImageService {
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    public ImageServiceJpa(ImageRepository imageRepository, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
    }

    @Override
    public List<ImageDTO> findAllImages() {
        List<ImageEntity> images = (List<ImageEntity>) imageRepository.findAll();
        return images.stream()
                .map(imageMapper::imageEntityToImageDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ImageDTO> findImageByID(UUID id) {
        Optional<ImageEntity> optionalImageEntity = imageRepository.findById(id);
        ImageEntity image = optionalImageEntity.orElseThrow(() -> new NotFoundException("Image not found with id: " + id));
        return Optional.ofNullable(imageMapper.imageEntityToImageDto(image));
    }

    @Override
    public ImageDTO saveImage(ImageDTO dto) {
        ImageEntity imageEntity = imageMapper.imageDtoToImageEntity(dto);
        ImageEntity savedImage = imageRepository.save(imageEntity);
        return imageMapper.imageEntityToImageDto(savedImage);
    }

    @Override
    public void deleteImage(UUID id) {
        if (!imageRepository.existsById(id)) {
            throw new NotFoundException("Image not found with id: " + id);
        }
        imageRepository.deleteById(id);
    }
}
