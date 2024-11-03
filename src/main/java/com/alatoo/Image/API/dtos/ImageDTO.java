package com.alatoo.Image.API.dtos;

import com.alatoo.Image.API.entities.AlbumEntity;
import com.alatoo.Image.API.entities.UserEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
    private UUID imageId;

    private String url;

    private String name;

    private LocalDateTime uploadedAt;

    private String description;

    private UUID userId;

    private UUID albumId;
}
