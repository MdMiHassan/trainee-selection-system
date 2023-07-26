package com.example.tss.util;

import java.util.Arrays;
import java.util.List;

public class FileUtils {
    public static String extractFileExtension(String fileName) {
        if (fileName != null && !fileName.isEmpty()) {
            int lastDotIndex = fileName.lastIndexOf(".");
            if (lastDotIndex != -1) {
                return fileName.substring(lastDotIndex + 1);
            }
        }
        return "";
    }

    public static boolean isValidFileType(String type) {
        List<String> validFileTypes = Arrays.asList("jpeg", "jpg", "png", "pdf");
        return validFileTypes.contains(type);
    }
}
