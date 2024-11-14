package com.alatoo.Image.API.services.Impl;

import com.alatoo.Image.API.dtos.AlbumDTO;
import com.alatoo.Image.API.entities.AlbumEntity;
import com.alatoo.Image.API.entities.UserEntity;
import com.alatoo.Image.API.exceptions.NotFoundException;
import com.alatoo.Image.API.mappers.AlbumMapper;
import com.alatoo.Image.API.repositories.AlbumRepository;
import com.alatoo.Image.API.services.AlbumService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AlbumServiceJpa implements AlbumService {
    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;

    public AlbumServiceJpa(AlbumRepository albumRepository, AlbumMapper albumMapper) {
        this.albumRepository = albumRepository;
        this.albumMapper = albumMapper;
    }


    @Override
    public List<AlbumDTO> findAllAlbums() {
        List<AlbumEntity> albums = (List<AlbumEntity>) albumRepository.findAll();
        return albums.stream()
                .map(albumMapper::albumEntityToAlbumDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AlbumDTO> findAlbumByID(UUID id) {
        Optional<AlbumEntity> optionalAlbum = albumRepository.findById(id);
        AlbumEntity albumEntity = optionalAlbum.orElseThrow(() -> new NotFoundException("Album not found with id: " + id));
        return Optional.of(albumMapper.albumEntityToAlbumDto(albumEntity));
    }

    @Override
    public AlbumDTO saveAlbum(AlbumDTO dto) {
        AlbumEntity albumEntity = albumMapper.albumDtoToAlbumEntity(dto);
        AlbumEntity savedAlbum = albumRepository.save(albumEntity);
        return albumMapper.albumEntityToAlbumDto(savedAlbum);
    }

    @Override
    public void deleteAlbum(UUID id) {
        if (!albumRepository.existsById(id)) {
            throw new NotFoundException("Album not found with id: " + id);
        }
        albumRepository.deleteById(id);
    }

    @Override
    public AlbumEntity getDefaultAlbum(UserEntity user) {
        if (albumRepository.existsByUserAndName(user, "All images")) {
            return albumRepository.getAlbumEntityByName("All images");
        } else {
            AlbumEntity album = AlbumEntity.builder()
                    .user(user)
                    .name("All images")
                    .description("Default album to images")
                    .build();
            return albumRepository.save(album);
        }
    }

    @Override
    public List<AlbumDTO> findAlbumsByUserId(UUID id) {
        List<AlbumEntity> albums = albumRepository.getAlbumEntitiesByUserId(id);
        return albums.stream()
                .map(albumMapper::albumEntityToAlbumDto)
                .collect(Collectors.toList());
    }
}
