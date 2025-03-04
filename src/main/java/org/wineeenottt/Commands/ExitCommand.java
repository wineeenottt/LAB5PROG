package org.wineeenottt.Commands;

/**
 * Класс ExitCommand реализует интерфейс Command и представляет команду завершения работы программы.
 * При выполнении команды программа завершает свою работу с выводом соответствующего сообщения.
 */
public class ExitCommand implements Command {

    /**
     * Конструктор класса ExitCommand.
     * Создает объект команды завершения работы программы.
     */
    public ExitCommand() {
    }

    /**
     * Метод, выполняющий команду завершения работы программы.
     * После вывода сообщения о завершении программа завершает свою работу с кодом 0.
     */
    @Override
    public void execute() {
        System.out.println("Завершение работы программы");
        System.exit(0);
    }

    /**
     * Метод, возвращающий описание команды.
     *
     * @return строка с описанием команды, указывающая, что команда завершает работу программы.
     */
    @Override
    public String getDescription() {
        return "завершает работу программы";
    }
}