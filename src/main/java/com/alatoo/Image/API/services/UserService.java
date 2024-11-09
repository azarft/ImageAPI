package com.alatoo.Image.API.services;

import com.alatoo.Image.API.dtos.UserDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<UserDTO> findAllUsers();

    Optional<UserDTO> findUserByID(UUID id);

    UserDTO saveUser(UserDTO dto);

    void deleteUser(UUID id);
}
