package com.alatoo.Image.API.repositories;

import com.alatoo.Image.API.entities.AlbumEntity;
import com.alatoo.Image.API.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface AlbumRepository extends JpaRepository<AlbumEntity, UUID> {
    boolean existsByUserAndName(UserEntity user, String name);
    AlbumEntity getAlbumEntityByName(String name);
    List<AlbumEntity> getAlbumEntitiesByUserId(UUID userId);
}
