package edu.ccrm.config;

import java.util.Properties;
import java.nio.file.Paths;
import java.nio.file.Path;

public class AppConfig {
    private static volatile AppConfig instance;
    private final Properties properties;
    
    // Static nested class for configuration constants
    public static class Constants {
        public static final String APP_NAME = "app.name";
        public static final String DATA_FOLDER = "data.folder";
        public static final String EXPORT_FOLDER = "export.folder";
        public static final String BACKUP_FOLDER = "backup.folder";
        public static final String MAX_CREDITS_PER_SEMESTER = "max.credits.semester";
        public static final int DEFAULT_MAX_CREDITS = 18;
    }
    
    private AppConfig() {
        properties = new Properties();
        loadDefaultProperties();
    }
    
    public static AppConfig getInstance() {
        if (instance == null) {
            synchronized (AppConfig.class) {
                if (instance == null) {
                    instance = new AppConfig();
                }
            }
        }
        return instance;
    }
    
    private void loadDefaultProperties() {
        properties.setProperty(Constants.APP_NAME, "Campus Course & Records Manager");
        properties.setProperty(Constants.DATA_FOLDER, "data");
        properties.setProperty(Constants.EXPORT_FOLDER, "exports");
        properties.setProperty(Constants.BACKUP_FOLDER, "backups");
        properties.setProperty(Constants.MAX_CREDITS_PER_SEMESTER, 
                             String.valueOf(Constants.DEFAULT_MAX_CREDITS));
    }
    
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public int getIntProperty(String key, int defaultValue) {
        try {
            return Integer.parseInt(getProperty(key));
        } catch (NumberFormatException | NullPointerException e) {
            return defaultValue;
        }
    }
    
    public String getAppName() {
        return properties.getProperty(Constants.APP_NAME, "CCRM");
    }

    public int getMaxCreditsPerSemester() {
        return getIntProperty(Constants.MAX_CREDITS_PER_SEMESTER, Constants.DEFAULT_MAX_CREDITS);
    }
    
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
    
    public void displayJavaPlatformInfo() {
        System.out.println("\n=== Java Platform Information ===");
        System.out.println("Java SE: Standard Edition - Desktop and server applications");
        System.out.println("Java ME: Micro Edition - Mobile and embedded devices");
        System.out.println("Java EE: Enterprise Edition - Large-scale enterprise applications");
        System.out.println("Current Runtime: Java SE " + System.getProperty("java.version"));
        System.out.println("================================\n");
    }

    public Path getExportFolderPath() {
        return Paths.get(getProperty(Constants.EXPORT_FOLDER));
    }

    public Path getBackupFolderPath() {
        return Paths.get(getProperty(Constants.BACKUP_FOLDER));
    }
}
