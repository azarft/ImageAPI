package com.alatoo.Image.API.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "albums")
public class AlbumEntity {
    @Id
    @GeneratedValue
    private UUID albumId;

    private String name;

    private String description;

    @ManyToOne
    private UserEntity user;
}
