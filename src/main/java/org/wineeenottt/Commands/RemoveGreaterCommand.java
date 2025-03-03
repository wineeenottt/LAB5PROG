package org.wineeenottt.Commands;

import org.wineeenottt.Collection.CollectionManager;
import org.wineeenottt.Utility.RouteFieldsReader;

/**
 * Класс RemoveGreaterCommand реализует интерфейс CommandWithArguments и представляет команду,
 * которая удаляет из коллекции все элементы, ID которых превышает заданный.
 */
public class RemoveGreaterCommand implements CommandWithArguments {

    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     * Используется для управления коллекцией и удаления элементов.
     */
    private final CollectionManager collectionManager;

    /**
     * Поле, хранящее аргументы команды.
     */
    private String[] commandArguments;

    /**
     * Конструктор класса RemoveGreaterCommand.
     *
     * @param collectionManager объект класса CollectionManager, используемый для управления коллекцией.
     */
    public RemoveGreaterCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Метод, выполняющий команду. Удаляет из коллекции все элементы, ID которых превышает заданный.
     * В случае ошибки (например, если аргумент не указан или имеет неверный формат) выводит соответствующее сообщение.
     */
    @Override
    public void execute() {
        try {
            int id = Integer.parseInt(commandArguments[0]);
            if (id == 0) {
                System.err.println("ID не может быть равен нулю");
                return;
            }
            if (collectionManager.containsIdRoute(id)) {
                collectionManager.removeGreater(id);
                System.out.println("Элементы коллекции удалены");
            } else {
                System.out.println("Данного элемента коллекции не существует");
            }
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("Не указаны аргументы команды.");
        } catch (NumberFormatException ex) {
            System.err.println("Формат аргумента не соответствует целочисленному: " + ex.getMessage());
        }
    }

    /**
     * Метод, возвращающий описание команды.
     *
     * @return строка с описанием команды, указывающая, что команда удаляет из коллекции все элементы,
     * ID которых превышает заданный.
     */
    @Override
    public String getDescription() {
        return "удаляет из коллекции все элементы, ID которых превышает заданный";
    }

    /**
     * Метод, получающий аргументы команды.
     *
     * @param commandArguments массив строк, содержащий аргументы команды.
     */
    @Override
    public void getCommandArguments(String[] commandArguments) {
        this.commandArguments = commandArguments;
    }
}