package com.alatoo.Image.API.services.Impl;

import com.alatoo.Image.API.dtos.AlbumDTO;
import com.alatoo.Image.API.entities.AlbumEntity;
import com.alatoo.Image.API.entities.UserEntity;
import com.alatoo.Image.API.exceptions.NotFoundException;
import com.alatoo.Image.API.mappers.AlbumMapper;
import com.alatoo.Image.API.repositories.AlbumRepository;
import com.alatoo.Image.API.services.AlbumService;
import com.alatoo.Image.API.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AlbumServiceJpa implements AlbumService {
    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;
    private final UserService userService;

    public AlbumServiceJpa(AlbumRepository albumRepository, AlbumMapper albumMapper, UserService userService) {
        this.albumRepository = albumRepository;
        this.albumMapper = albumMapper;
        this.userService = userService;
    }


    @Override
    public List<AlbumDTO> findAllAlbums() {
        UserEntity user = userService.getCurrentUser();
        List<AlbumEntity> albums = albumRepository.getAlbumEntitiesByUserId(user.getId());
        return albums.stream()
                .map(albumMapper::albumEntityToAlbumDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AlbumDTO> findAlbumByID(UUID id) {
        UserEntity user = userService.getCurrentUser();
        Optional<AlbumEntity> optionalAlbum = albumRepository.findByAlbumIdAndUserId(id, user.getId());
        AlbumEntity albumEntity = optionalAlbum.orElseThrow(() -> new NotFoundException("Album not found with id: " + id));
        return Optional.of(albumMapper.albumEntityToAlbumDto(albumEntity));
    }

    @Override
    public AlbumDTO saveAlbum(AlbumDTO dto) {
        UserEntity user = userService.getCurrentUser();
        AlbumEntity albumEntity = albumMapper.albumDtoToAlbumEntity(dto);
        albumEntity.setUser(user);
        AlbumEntity savedAlbum = albumRepository.save(albumEntity);
        return albumMapper.albumEntityToAlbumDto(savedAlbum);
    }

    @Override
    public void deleteAlbum(UUID id) {
        UserEntity user = userService.getCurrentUser();
        if (!albumRepository.existsByAlbumIdAndUserId(id, user.getId())) {
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
