package com.alatoo.Image.API.controllers;


import com.alatoo.Image.API.dtos.UserDTO;
import com.alatoo.Image.API.dtos.authorization.AuthLoginDto;
import com.alatoo.Image.API.dtos.authorization.AuthRegistrationDTO;
import com.alatoo.Image.API.dtos.authorization.JwtTokenDto;
import com.alatoo.Image.API.dtos.authorization.RefreshTokenRequestDTO;
import com.alatoo.Image.API.entities.RefreshToken;
import com.alatoo.Image.API.entities.UserEntity;
import com.alatoo.Image.API.services.JwtService;
import com.alatoo.Image.API.services.RefreshTokenService;
import com.alatoo.Image.API.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Validated @RequestBody AuthRegistrationDTO authRegistrationDTO){
        return userService.signUp(authRegistrationDTO);
    }

    @PostMapping("/login")
    public JwtTokenDto AuthenticateAndGetToken(@RequestBody AuthLoginDto authRequestDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if(authentication.isAuthenticated()){
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequestDTO.getUsername());
            return JwtTokenDto.builder()
                    .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername()))
                    .token(refreshToken.getToken())
                    .build();

        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }
    @PostMapping("/refreshToken")
    public JwtTokenDto refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(userInfo -> {
                    String accessToken = jwtService.GenerateToken(userInfo.getUsername());
                    return JwtTokenDto.builder()
                            .accessToken(accessToken)
                            .token(refreshTokenRequestDTO.getToken()).build();
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
    }

    @GetMapping
    public Object getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserEntity userDetail) {
            return new UserDTO(userDetail);
        }
        return principal;
    }
}
