package com.alatoo.Image.API.repositories;

import com.alatoo.Image.API.entities.AlbumEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AlbumRepository extends JpaRepository<AlbumEntity, UUID> {

}
