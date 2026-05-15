package org.example.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                properties.setProperty("monitor.directory", ".");
                properties.setProperty("alert.file", "logs/duplicates_report.txt");
            } else {
                properties.load(input);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getMonitorDirectory() {
        return properties.getProperty("monitor.directory");
    }

    public static String getAlertFile() {
        return properties.getProperty("alert.file");
    }

    public static String[] getErrorKeywords() {
        String keywords = properties.getProperty("error.keywords", "error,critical");
        return keywords.split(",");
    }
}
