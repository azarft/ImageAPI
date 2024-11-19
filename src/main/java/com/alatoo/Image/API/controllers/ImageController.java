package com.alatoo.Image.API.controllers;

import com.alatoo.Image.API.dtos.ImageDTO;
import com.alatoo.Image.API.exceptions.NotFoundException;
import com.alatoo.Image.API.services.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Log4j2
public class ImageController {
    private final ImageService imageService;

    private final String IMAGE_PATH = "/images";
    private final String ID_PATH = "/{id}";

    @GetMapping(IMAGE_PATH)
    @PreAuthorize("hasRole('ADMIN')")
    public List<ImageDTO> getAllImages() {
        log.info("Fetching all images");
        return imageService.findAllImages();
    }

    @GetMapping(IMAGE_PATH + ID_PATH)
    public ImageDTO getImageById(@PathVariable UUID id) {
        log.info("Fetching image with id: {}", id);
        return imageService.findImageByID(id).orElseThrow(() -> new NotFoundException("Image not found with id: " + id));
    }

    @PostMapping(IMAGE_PATH)
    public ImageDTO createImage(@RequestBody ImageDTO imageDTO) {
        log.info("Creating a new image with details: {}", imageDTO);
        return imageService.saveImage(imageDTO);
    }

    @PutMapping(IMAGE_PATH + ID_PATH)
    public ImageDTO updateImage(@PathVariable UUID id, @Validated @RequestBody ImageDTO imageDTO) {
        imageService.findImageByID(id).orElseThrow(() -> new NotFoundException("Image not found with id: " + id));
        imageDTO.setImageId(id);
        return imageService.saveImage(imageDTO);
    }

    @DeleteMapping(IMAGE_PATH + ID_PATH)
    public void deleteImage(@PathVariable UUID id) {
        imageService.deleteImage(id);
    }

    @GetMapping("/users/{userId}/images")
    public List<ImageDTO> getImagesByUserId(@PathVariable UUID userId) {
        return imageService.findImagesByUserId(userId);
    }

    @GetMapping("/albums/{albumId}/images")
    public List<ImageDTO> getImagesByAlbumId(@PathVariable UUID albumId) {
        return imageService.findImagesByAlbumId(albumId);
    }

    @GetMapping("/users/{userId}/albums/{albumId}/images")
    public List<ImageDTO> getImagesByUserIdAndAlbumId(@PathVariable UUID userId, @PathVariable UUID albumId) {
        return imageService.findImagesByUserIdAndByAlbumId(userId, albumId);
    }
}
