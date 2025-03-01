package org.wineeenottt.Commands;

/**
 * Интерфейс CommandWithArguments расширяет интерфейс Command и предназначен для команд,
 * которые принимают аргументы, вводимые с новой строки.
 * Реализующие классы должны предоставить реализацию метода getCommandArguments(),
 * который обрабатывает переданные аргументы команды.
 */
public interface CommandWithArguments extends Command {

    /**
     * Метод, который получает и обрабатывает аргументы команды.
     * Реализующие классы должны использовать этот метод для извлечения и обработки
     * аргументов, переданных команде.
     *
     * @param commandArguments массив строк, содержащий аргументы команды.
     */
    void getCommandArguments(String[] commandArguments);
}