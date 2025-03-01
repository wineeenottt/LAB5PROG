package org.wineeenottt.Commands;

import java.util.Map;

/**
 * Класс HelpCommand реализует интерфейс Command и представляет команду вывода справки.
 * Команда выводит описание всех доступных в программе команд.
 */
public class HelpCommand implements Command {

    /**
     * Коллекция, содержащая объекты всех доступных в программе команд.
     * Ключом является имя команды, а значением — объект команды.
     */
    private final Map<String, Command> commandMap;

    /**
     * Конструктор класса HelpCommand.
     *
     * @param commandMap коллекция, содержащая все доступные команды.
     */
    public HelpCommand(Map<String, Command> commandMap) {
        this.commandMap = commandMap;
    }

    /**
     * Метод, выполняющий команду. Выводит описание всех доступных в программе команд.
     * Для каждой команды выводится её имя и описание.
     */
    @Override
    public void execute() {
        for (Map.Entry<String, Command> entry : commandMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue().getDescription());
        }
    }

    /**
     * Метод, возвращающий описание данной команды.
     *
     * @return строка с описанием команды, указывающая, что команда выводит справку по всем командам.
     */
    @Override
    public String getDescription() {
        return "выводит справку по всем командам";
    }
}