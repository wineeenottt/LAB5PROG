package org.wineeenottt.Commands;

import java.util.ArrayList;

/**
 * Класс HistoryCommand реализует интерфейс Command и представляет команду вывода истории выполненных команд.
 * Команда выводит список последних 11 выполненных команд.
 */
public class HistoryCommand implements Command {

    /**
     * Список, хранящий историю выполненных команд.
     */
    private ArrayList<String> commandsHistoryList;

    /**
     * Конструктор класса HistoryCommand.
     *
     * @param commandsHistoryList список, содержащий историю выполненных команд.
     */
    public HistoryCommand(ArrayList<String> commandsHistoryList) {
        this.commandsHistoryList = commandsHistoryList;
    }

    /**
     * Метод, выполняющий команду. Выводит список последних выполненных команд.
     * Если список пуст, выводится соответствующее сообщение.
     */
    @Override
    public void execute() {
        System.out.println("History: ");
        for (String str : commandsHistoryList) {
            System.out.println(str);
        }
    }

    /**
     * Метод, возвращающий описание команды.
     *
     * @return строка с описанием команды, указывающая, что команда выводит последние 11 выполненных команд.
     */
    @Override
    public String getDescription() {
        return "вывести последние 11 команд";
    }
}