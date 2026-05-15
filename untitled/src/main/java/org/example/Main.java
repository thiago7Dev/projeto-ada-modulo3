package org.example;

import org.example.config.Config;
import org.example.monitor.LogMonitor;
import org.example.processor.LogProcessor;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        LogProcessor mockProcessor = logLine -> {
            System.out.println("[Pessoa B Mock] Recebido: " + logLine);
        };

        String directoryToWatch = Config.getMonitorDirectory();
        LogMonitor monitor = new LogMonitor(directoryToWatch, mockProcessor);

        try {
            monitor.start();
        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao iniciar monitor: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
