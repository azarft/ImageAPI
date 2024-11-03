package com.alatoo.Image.API.mappers;

import com.alatoo.Image.API.dtos.UserDTO;
import com.alatoo.Image.API.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserEntity userDtoToUserEntity(UserDTO dto);

    UserDTO userEntityToUserDto(UserEntity user);
}
