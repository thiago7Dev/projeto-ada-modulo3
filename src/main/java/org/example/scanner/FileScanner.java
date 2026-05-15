package org.example.scanner;

import org.example.processor.DuplicateProcessor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileScanner {
    private final Path rootPath;
    private final DuplicateProcessor processor;

    public FileScanner(String path, DuplicateProcessor processor) {
        this.rootPath = Paths.get(path);
        this.processor = processor;
    }

    public void scan() throws IOException {
        Map<String, List<Path>> fileHashes = new HashMap<>();

        Files.walk(rootPath)
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    try {
                        String hash = calculateHash(path);
                        fileHashes.computeIfAbsent(hash, k -> new ArrayList<>()).add(path);
                    } catch (Exception e) {
                        System.err.println("Erro ao processar arquivo " + path + ": " + e.getMessage());
                    }
                });

        Map<String, List<Path>> duplicates = fileHashes.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        processor.processDuplicates(duplicates);
    }

    private String calculateHash(Path path) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (InputStream is = Files.newInputStream(path)) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = is.read(buffer)) > 0) {
                digest.update(buffer, 0, read);
            }
        }
        byte[] hashBytes = digest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : hashBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
