package com.example.tss.util;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Component
public class ImageUtils {
    public static String encodeImageToBase64(byte[] imageBytes) throws IOException {
        String imageType = ImageIO.getImageReaders(new ByteArrayInputStream(imageBytes)).next().getFormatName().toLowerCase();
        boolean isValidImageType = isValidImageType(imageType);
        StringBuilder imageString = new StringBuilder("data:image/");
        if (isValidImageType) {
            imageString.append(imageType + ";");
        } else {
            throw new IOException();
        }
        imageString.append(Base64.getEncoder().encodeToString(imageBytes));
        return imageString.toString();
    }
    public static boolean isValidImageType(String imageType) {
        List<String> validImageTypes = Arrays.asList("jpeg", "jpg", "png");
        return validImageTypes.contains(imageType);
    }
}
