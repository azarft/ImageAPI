package com.alatoo.Image.API.dtos;

import com.alatoo.Image.API.entities.UserEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDTO {
    private UUID albumId;

    private String name;

    private String description;

    private UUID userId;
}
