package com.alatoo.Image.API.bootstrap;

import com.alatoo.Image.API.entities.AlbumEntity;
import com.alatoo.Image.API.entities.ImageEntity;
import com.alatoo.Image.API.entities.UserEntity;
import com.alatoo.Image.API.enums.Role;
import com.alatoo.Image.API.repositories.AlbumRepository;
import com.alatoo.Image.API.repositories.ImageRepository;
import com.alatoo.Image.API.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InitData implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        UserEntity user = UserEntity.builder()
                .email("argoazanov@gmail.com")
                .username("azarft")
                .role(Role.ROLE_ADMIN)
                .password(passwordEncoder.encode("password"))
                .build();
        UserEntity savedUser = userRepository.save(user);

        for (int i = 0; i < 10; i++) {
            AlbumEntity album = AlbumEntity.builder()
                    .name("Album" + i)
                    .description("Description of album " + i)
                    .user(savedUser)
                    .build();
            AlbumEntity savedAlbum = albumRepository.save(album);

            ImageEntity image = ImageEntity.builder()
                    .name("Image " + i)
                    .description("Description of image " + i)
                    .uploadedAt(LocalDateTime.now())
                    .user(savedUser)
                    .album(savedAlbum)
                    .imageFile("images/image" + i)
                    .build();
            ImageEntity savedImage = imageRepository.save(image);
        }
    }
}
