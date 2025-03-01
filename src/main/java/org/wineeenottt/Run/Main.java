package org.wineeenottt.Run;

/**
 * Класс Main является точкой входа в программу.
 * Он проверяет аргументы командной строки и запускает приложение с указанным файлом для хранения данных.
 * Если аргументы командной строки отсутствуют, используется путь по умолчанию.
 */
public class Main {

    /**
     * Основной метод, который запускает программу.
     *
     * @param args аргументы командной строки. Первый аргумент (если присутствует) должен содержать путь к файлу для хранения данных.
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            if (!args[0].isEmpty()) {
                Application application = new Application();
                application.start(args[0]);
            }
        } else {
            Application application = new Application();
            application.start("Files/RouteStorage");
        }
    }
}