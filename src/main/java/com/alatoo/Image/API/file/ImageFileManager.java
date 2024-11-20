package com.alatoo.Image.API.file;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Component
public class ImageFileManager {

    public String saveImageFile(String base64) throws IOException {
        String imageData;
        if (base64.contains(",")) {
            imageData = base64.split(",")[1];
        } else {
            imageData = base64;
        }

        byte[] imageBytes = Base64.getDecoder().decode(imageData);

        File uploadsDir = new File("uploads");
        if (!uploadsDir.exists() && !uploadsDir.mkdirs()) {
            throw new IOException("Failed to create uploads directory");
        }

        String filename = UUID.randomUUID().toString() + ".jpg";

        File imageFile = new File(uploadsDir, filename);

        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            fos.write(imageBytes);
        }

        System.out.println("Image saved to: " + imageFile.getAbsolutePath());
        return imageFile.getAbsolutePath();
    }
}
