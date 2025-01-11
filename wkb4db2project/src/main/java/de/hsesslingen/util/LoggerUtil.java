package de.hsesslingen.util;

import java.time.LocalDateTime;

public class LoggerUtil {

    // Log info message
    public static void logInfo(String message) {
        System.out.println("[INFO] " + LocalDateTime.now() + " - " + message);
    }

    // Log error message
    public static void logError(String message, Exception e) {
        System.err.println("[ERROR] " + LocalDateTime.now() + " - " + message);
        e.printStackTrace();
    }
}
