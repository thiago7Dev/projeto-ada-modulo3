package org.example;

import org.example.config.Config;
import org.example.processor.DuplicateAlertProcessor;
import org.example.processor.DuplicateProcessor;
import org.example.scanner.FileScanner;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        DuplicateProcessor processor = new DuplicateAlertProcessor();
        String directoryToScan = Config.getMonitorDirectory();
        
        FileScanner scanner = new FileScanner(directoryToScan, processor);

        try {
            System.out.println("Iniciando busca por duplicados em: " + directoryToScan);
            scanner.scan();
        } catch (IOException e) {
            System.err.println("Erro durante o scan: " + e.getMessage());
        }
    }
}
