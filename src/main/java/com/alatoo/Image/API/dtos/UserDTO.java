package com.alatoo.Image.API.dtos;

import com.alatoo.Image.API.entities.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private UUID id;

    private String username;

    private String email;

    public UserDTO(UserEntity user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
