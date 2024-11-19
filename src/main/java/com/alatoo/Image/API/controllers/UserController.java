package com.alatoo.Image.API.controllers;

import com.alatoo.Image.API.dtos.UserDTO;
import com.alatoo.Image.API.exceptions.NotFoundException;
import com.alatoo.Image.API.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Role;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Log4j2
public class UserController {
    private final UserService userService;

    private final String USER_PATH = "/users";
    private final String ID_PATH = "/{id}";
    private final String ADMIN_PATH = "/admin";

    @GetMapping(ADMIN_PATH + USER_PATH)
    public List<UserDTO> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping(ADMIN_PATH + USER_PATH + ID_PATH)
    public UserDTO getById(@PathVariable UUID id) {
        return userService.findUserByID(id).orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    @PostMapping(USER_PATH)
    public UserDTO createUser(@Validated @RequestBody UserDTO userDTO) {
        return userService.saveUser(userDTO);
    }

    @PutMapping(ID_PATH)
    public UserDTO updateUser(@PathVariable UUID id, @Validated @RequestBody UserDTO userDTO) {
        userService.findUserByID(id).orElseThrow(() -> new NotFoundException("User not found with id: " + id));
        userDTO.setId(id);
        return userService.saveUser(userDTO);
    }
}
