package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameLogger {

    private static final String LOG_FILE = "game.log"; // Путь к файлу лога
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final Logger logger = Logger.getLogger(GameLogger.class.getName()); // Получаем экземпляр Logger

    public static void log(String message) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true); // true - режим добавления (append)
             PrintWriter pw = new PrintWriter(fw)) {

            String timestamp = LocalDateTime.now().format(formatter);
            pw.println("[" + timestamp + "] " + message);

        } catch (IOException e) {
            System.err.println("Ошибка при записи в лог: " + e.getMessage());
            logger.log(Level.SEVERE, "Ошибка при записи в лог", e); // Логируем ошибку с помощью Logger
        }
    }

    public static void log(Level level, String message, Throwable e) {
        logger.log(level, message, e);
    }
    public static void logInfo(String message) {
        logger.log(Level.INFO, message);
    }

}