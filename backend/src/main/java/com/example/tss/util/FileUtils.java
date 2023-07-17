package com.example.tss.util;

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
}
