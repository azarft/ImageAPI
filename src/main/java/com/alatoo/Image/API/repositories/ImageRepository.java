package com.alatoo.Image.API.repositories;

import com.alatoo.Image.API.entities.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<ImageEntity, UUID> {

}
