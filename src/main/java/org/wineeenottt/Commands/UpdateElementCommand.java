package org.wineeenottt.Commands;

import org.wineeenottt.Collection.CollectionManager;
import org.wineeenottt.IO.UserIO;

/**
 * Класс, реализующий команду обновления элемента коллекции по его ID.
 * Позволяет изменять значения полей выбранного элемента.
 */
public class UpdateElementCommand implements CommandWithArguments {

    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     */
    private CollectionManager collectionManager;

    /**
     * Поле, хранящее ссылку на объект класса UserIO для взаимодействия с пользователем.
     */
    private UserIO userIO;

    /**
     * Массив, хранящий аргументы команды.
     */
    private String[] commandArguments;

    /**
     * Флаг, указывающий, выполняется ли команда в режиме скрипта.
     */
    private boolean isScriptExecution;

    /**
     * Конструктор класса UpdateElementCommand.
     *
     * @param collectionManager менеджер коллекции, который будет использоваться для обновления элемента.
     * @param userIO            объект для взаимодействия с пользователем.
     */
    public UpdateElementCommand(CollectionManager collectionManager, UserIO userIO) {
        this.collectionManager = collectionManager;
        this.userIO = userIO;
    }

    /**
     * Метод, исполняющий команду.
     * Пользователь может изменять поля элемента коллекции, указанного по ID.
     * Если элемент с указанным ID не существует, выводится сообщение об ошибке.
     * Пользователь может прервать выполнение команды, введя "stop".
     */
    @Override
    public void execute() {
        try {
            if (collectionManager.containsIdRoute(Integer.parseInt(commandArguments[0]))) {
                if (!isScriptExecution) {
                    userIO.printCommandText(collectionManager.getFieldNames());
                    userIO.printCommandText("Напишите stop, если хотите прервать изменение элемента коллекции\n");
                    userIO.printCommandText("Введите название поля и его новое значение:\n");
                }

                String[] commandWords = new String[0];
                do {
                    try {
                        commandWords = userIO.readLine().trim().split("\\s+");

                        if (commandWords.length == 1) {
                            collectionManager.update(Integer.parseInt(commandArguments[0]), commandWords[0], "");
                        } else {
                            collectionManager.update(Integer.parseInt(commandArguments[0]), commandWords[0], commandWords[1]);
                        }
                    } catch (IndexOutOfBoundsException ex) {
                        System.err.println("Не указано поле/значение");
                    }
                } while (!commandWords[0].equals("stop"));
            } else {
                System.err.println("Элемента с данным id в коллекции не существует");
            }
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("Не указаны все аргументы команды");
        } catch (NumberFormatException ex) {
            System.err.println("Формат аргумента не соответствует целочисленному " + ex.getMessage());
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return строка с описанием команды.
     */
    @Override
    public String getDescription() {
        return "изменяет указанное поле выбранного по ID элемента коллекции";
    }

    /**
     * Устанавливает аргументы команды.
     *
     * @param commandArguments массив аргументов команды.
     */
    @Override
    public void getCommandArguments(String[] commandArguments) {
        this.commandArguments = commandArguments;
    }

    /**
     * Устанавливает флаг выполнения команды в режиме скрипта.
     *
     * @param isScriptExecution true, если команда выполняется в режиме скрипта, иначе false.
     */
    public void setIsScriptExecution(boolean isScriptExecution) {
        this.isScriptExecution = isScriptExecution;
    }
}