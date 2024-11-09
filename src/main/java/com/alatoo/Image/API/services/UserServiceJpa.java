package com.alatoo.Image.API.services;

import com.alatoo.Image.API.dtos.UserDTO;
import com.alatoo.Image.API.entities.UserEntity;
import com.alatoo.Image.API.mappers.UserMapper;
import com.alatoo.Image.API.repositories.UserRepository;
import com.alatoo.Image.API.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceJpa implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceJpa(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<UserEntity> users = (List<UserEntity>) userRepository.findAll();
        return users.stream()
                .map(userMapper::userEntityToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> findUserByID(UUID id) {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        UserEntity user = optionalUserEntity.orElseThrow(() -> new NotFoundException("User not found with id: " + id));
        return Optional.of(userMapper.userEntityToUserDto(user));
    }

    @Override
    public UserDTO saveUser(UserDTO dto) {
        UserEntity user = userMapper.userDtoToUserEntity(dto);
        UserEntity savedUser = userRepository.save(user);
        return userMapper.userEntityToUserDto(savedUser);
    }

    @Override
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
