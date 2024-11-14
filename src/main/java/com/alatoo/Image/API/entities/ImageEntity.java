package com.alatoo.Image.API.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "images")
public class ImageEntity {
    @Id
    @GeneratedValue
    private UUID imageId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String imageFile;

    private String name;

    private LocalDateTime uploadedAt;

    private String description;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private AlbumEntity album;
}
