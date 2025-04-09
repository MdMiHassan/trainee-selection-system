package com.example.tss.util;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Component
public class ImageUtils {
    public static String encodeImageToBase64(byte[] imageBytes) throws IOException {
        System.out.println("Image length" + imageBytes.length);
//        String imageType = ImageIO.getImageReaders(new ByteArrayInputStream(imageBytes)).next().getFormatName().toLowerCase();
//        boolean isValidImageType = isValidImageType(imageType);
        //        if (isValidImageType) {
//            imageString.append(imageType + ";");
//        } else {
//            throw new IOException();
//        }
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);
    }

    public static boolean isValidImageType(String imageType) {
        List<String> validImageTypes = Arrays.asList("jpeg", "jpg", "png");
        return validImageTypes.contains(imageType);
    }
}
