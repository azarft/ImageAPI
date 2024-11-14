package com.alatoo.Image.API.mappers;

import com.alatoo.Image.API.dtos.UserDTO;
import com.alatoo.Image.API.dtos.authorization.AuthRegistrationDTO;
import com.alatoo.Image.API.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserEntity userDtoToUserEntity(UserDTO dto);
    UserEntity registrationDtoToUserEntity(AuthRegistrationDTO dto);

    UserDTO userEntityToUserDto(UserEntity user);
}
