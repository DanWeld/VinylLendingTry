package com.vinyl.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class Logger {
    private static final HashMap<File, Logger> instances = new HashMap<>();
    private final File logFile;
    private final CurrentTime currentTime;

    private Logger(File logFile) {
        this.logFile = logFile;
        this.currentTime = CurrentTime.getInstance();
        
        // Create logs directory if it doesn't exist
        File logDir = logFile.getParentFile();
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
        
        // Log initialization
        try {
            log("Logger initialized for file: " + logFile.getName(), "SYSTEM");
        } catch (IOException e) {
            System.err.println("Failed to initialize logger: " + e.getMessage());
        }
    }

    public static synchronized Logger getInstance() {
        String date = CurrentTime.getInstance().getFormattedIsoDate();
        File logDir = new File("logs");
        File logFile = new File(logDir, "server_" + date + ".log");
        return getInstance(logFile);
    }

    public static synchronized Logger getInstance(File logFile) {
        if (!instances.containsKey(logFile)) {
            instances.put(logFile, new Logger(logFile));
        }
        return instances.get(logFile);
    }

    public void log(String message, String ipAddress) throws IOException {
        String timestamp = currentTime.getFormattedTime();
        String logMessage = String.format("[%s] [IP: %s] %s", timestamp, ipAddress, message);
        
        // Log to console
        System.out.println(logMessage);
        
        // Log to file
        try (FileWriter fileWriter = new FileWriter(logFile, true);
             PrintWriter writer = new PrintWriter(fileWriter)) {
            writer.println(logMessage);
            writer.flush();
        }
    }

    public File getLogFile() {
        return logFile;
    }

    // Method to get all available log files
    public static File[] getAvailableLogFiles() {
        return instances.keySet().toArray(new File[0]);
    }
} 