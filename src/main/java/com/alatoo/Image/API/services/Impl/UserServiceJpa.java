package com.alatoo.Image.API.services.Impl;

import com.alatoo.Image.API.dtos.UserDTO;
import com.alatoo.Image.API.dtos.authorization.AuthRegistrationDTO;
import com.alatoo.Image.API.entities.UserEntity;
import com.alatoo.Image.API.enums.Role;
import com.alatoo.Image.API.mappers.UserMapper;
import com.alatoo.Image.API.repositories.AlbumRepository;
import com.alatoo.Image.API.repositories.UserRepository;
import com.alatoo.Image.API.exceptions.NotFoundException;
import com.alatoo.Image.API.services.AlbumService;
import com.alatoo.Image.API.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceJpa implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AlbumRepository albumRepository;
    private final AlbumService albumService;

    public UserServiceJpa(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, AlbumRepository albumRepository, AlbumService albumService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.albumRepository = albumRepository;
        this.albumService = albumService;
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
        user.setRole(Role.USER);
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

    @Override
    public ResponseEntity<UserDTO> signUp(AuthRegistrationDTO authRegistrationDTO) {
        UserEntity user = userMapper.registrationDtoToUserEntity(authRegistrationDTO);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(authRegistrationDTO.getPassword()));

        UserEntity savedUser = userRepository.save(user);
        albumService.getDefaultAlbum(savedUser);

        UserDTO dto = userMapper.userEntityToUserDto(savedUser);
        return ResponseEntity.ok(dto);
    }

    @Override
    public UserEntity getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserEntity userDetails) {
            Optional<UserEntity> optionalUser = userRepository.findById(userDetails.getId());
            UserEntity user = optionalUser.orElseThrow(() -> new NotFoundException("User not found with id: " + userDetails.getId()));
            return user;
        } else {
            return null;
        }
    }
}
