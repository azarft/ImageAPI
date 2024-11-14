package com.alatoo.Image.API.controllers;

import com.alatoo.Image.API.dtos.AlbumDTO;
import com.alatoo.Image.API.exceptions.NotFoundException;
import com.alatoo.Image.API.services.AlbumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Log4j2
public class AlbumController {
    private final AlbumService albumService;

    private final String ALBUM_PATH = "/albums";
    private final String ID_PATH = "/{id}";

    @GetMapping(ALBUM_PATH)
    public List<AlbumDTO> getAllAlbums() {
        log.info("Fetching all albums");
        return albumService.findAllAlbums();
    }

    @GetMapping(ALBUM_PATH + ID_PATH)
    public AlbumDTO getAlbumById(@PathVariable UUID id) {
        log.info("Fetching album with id: {}", id);
        return albumService.findAlbumByID(id).orElseThrow(() -> new NotFoundException("Album not found with id: " + id));
    }

    @PostMapping(ALBUM_PATH)
    public AlbumDTO createAlbum(@Validated @RequestBody AlbumDTO albumDTO) {
        log.info("Creating a new album with details: {}", albumDTO);
        return albumService.saveAlbum(albumDTO);
    }

    @PutMapping(ALBUM_PATH + ID_PATH)
    public AlbumDTO updateAlbum(@PathVariable UUID id, @Validated @RequestBody AlbumDTO albumDTO) {
        log.info("Updating album with id: {}", id);
        albumService.findAlbumByID(id).orElseThrow(() -> new NotFoundException("Album not found with id: " + id));
        albumDTO.setAlbumId(id);
        return albumService.saveAlbum(albumDTO);
    }

    @DeleteMapping(ALBUM_PATH + ID_PATH)
    public void deleteAlbum(@PathVariable UUID id) {
        log.info("Deleting album with id: {}", id);
        albumService.deleteAlbum(id);
    }

    @GetMapping(ALBUM_PATH + "/user" + ID_PATH)
    public List<AlbumDTO> getAlbumsByUserId(@PathVariable UUID id) {
        log.info("Fetching albums by user id");
        return albumService.findAlbumsByUserId(id);
    }
}
