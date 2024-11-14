package com.alatoo.Image.API.repositories;

import com.alatoo.Image.API.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, UUID> {
    List<ImageEntity> getImageEntitiesByAlbumAlbumId(UUID album_albumId);
    List<ImageEntity> getImageEntitiesByUserId(UUID userId);
    List<ImageEntity> getImageEntitiesByUserIdAndAlbumAlbumId(UUID userId, UUID albumId);
}
