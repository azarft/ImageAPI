package com.alatoo.Image.API.services;

import com.alatoo.Image.API.dtos.AlbumDTO;
import com.alatoo.Image.API.dtos.UserDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlbumService {
    List<AlbumDTO> findAllAlbums();

    Optional<AlbumDTO> findAlbumByID(UUID id);

    AlbumDTO saveAlbum(AlbumDTO dto);

    void deleteAlbum(UUID id);
}
