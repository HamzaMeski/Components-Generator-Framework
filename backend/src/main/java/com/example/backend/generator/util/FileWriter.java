package com.example.backend.generator.util;

import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileWriter {

    public void writeFile(String path, String content) {
        try {
            Path filePath = Paths.get(path);
            Files.createDirectories(filePath.getParent());
            Files.writeString(filePath, content);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write file: " + path, e);
        }
    }
}