package org.example.processor;

import org.example.config.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class DuplicateAlertProcessor implements DuplicateProcessor {
    private final String reportFile;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public DuplicateAlertProcessor() {
        this.reportFile = Config.getAlertFile();
    }

    @Override
    public void processDuplicates(Map<String, List<Path>> duplicates) {
        if (duplicates.isEmpty()) {
            System.out.println("Nenhum arquivo duplicado encontrado.");
            return;
        }

        StringBuilder report = new StringBuilder();
        report.append("--- Relatório de Duplicados (").append(LocalDateTime.now().format(formatter)).append(") ---\n");

        duplicates.forEach((hash, paths) -> {
            report.append("Hash: ").append(hash).append("\n");
            paths.forEach(path -> report.append("  - ").append(path.toAbsolutePath()).append("\n"));
            report.append("\n");
        });

        writeReport(report.toString());
        System.out.println("Relatório gerado em: " + reportFile);
    }

    private void writeReport(String content) {
        try {
            Path path = Paths.get(reportFile);
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }
            Files.write(
                    path,
                    content.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            System.err.println("Erro ao gravar relatório: " + e.getMessage());
        }
    }
}
