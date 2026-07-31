package com.urbanpulse.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileStorageUtil {

    private static final String STORAGE_DIR = "uploads";

    private FileStorageUtil() {}

    public static String save(InputStream fileData, String originalFileName) throws IOException {
        Path dir = Paths.get(STORAGE_DIR);
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }

        String extension = "";
        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = originalFileName.substring(dotIndex);
        }

        String uniqueName = UUID.randomUUID() + extension;
        Path destination = dir.resolve(uniqueName);
        Files.copy(fileData, destination);

        return destination.toString();
    }
}