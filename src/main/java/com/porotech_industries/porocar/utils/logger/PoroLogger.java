package com.porotech_industries.porocar.utils.logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PoroLogger{
    private static boolean enabled;
    private static LogLevel activeLogLevel;

    public enum LogLevel{
        OFF(0), ERROR(1), WARN(2), INFO(3), DEBUG(4), VERBOSE(5);

        private final int value;
        LogLevel(int value){ this.value = value; }
        public int getValue() { return value; }
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    public void setLogLevel(LogLevel level) {
        activeLogLevel = level;
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private static void log(LogLevel level, String tag, String message, Object... args) {
        if (!enabled || level.getValue() > activeLogLevel.getValue()) {
            return;
        }

        String formattedMessage = args.length > 0 ? String.format(message, args) : message;

        String timestamp = LocalDateTime.now().format(formatter);
        String formatted = String.format("[%s] [%s] [%s] %s", timestamp, level.name(), tag, formattedMessage);

        System.out.println(formatted);
    }

    public static void error(String tag, String message, Object... args)   { log(LogLevel.ERROR, tag, message, args); }
    public static void warn(String tag, String message, Object... args)    { log(LogLevel.WARN, tag, message, args); }
    public static void info(String tag, String message, Object... args)    { log(LogLevel.INFO, tag, message, args); }
    public static void debug(String tag, String message, Object... args)   { log(LogLevel.DEBUG, tag, message, args); }
    public static void verbose(String tag, String message, Object... args) { log(LogLevel.VERBOSE, tag, message, args); }
}