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
        // Check for and remove the data URL prefix if it exists
        String imageData;
        if (base64.contains(",")) {
            imageData = base64.split(",")[1];
        } else {
            imageData = base64;
        }

        // Decode the base64 data into a byte array
        byte[] imageBytes = Base64.getDecoder().decode(imageData);

        // Create the 'uploads' directory if it doesn't exist
        File uploadsDir = new File("uploads");
        if (!uploadsDir.exists() && !uploadsDir.mkdirs()) {
            throw new IOException("Failed to create uploads directory");
        }

        // Generate a unique filename with a .jpg extension
        String filename = UUID.randomUUID().toString() + ".jpg"; // Use ".jpg" or determine dynamically if needed

        // Create the image file
        File imageFile = new File(uploadsDir, filename);

        // Write the image bytes to the file
        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            fos.write(imageBytes);
        }

        // Return the file path or URL for confirmation
        System.out.println("Image saved to: " + imageFile.getAbsolutePath());
        return imageFile.getAbsolutePath();
    }
}
