package org.wineeenottt.Commands;

import org.wineeenottt.Collection.CollectionManager;

/**
 * Класс RemoveByIdCommand реализует интерфейс CommandWithArguments и представляет команду,
 * которая удаляет элемент коллекции по указанному ID.
 */
public class RemoveByIdCommand implements CommandWithArguments {

    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     * Используется для управления коллекцией и удаления элемента по ID.
     */
    private CollectionManager collectionManager;

    /**
     * Поле, хранящее массив аргументов команды.
     */
    private String[] commandArguments;

    /**
     * Конструктор класса RemoveByIdCommand.
     *
     * @param collectionManager объект класса CollectionManager, используемый для управления коллекцией.
     */
    public RemoveByIdCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Метод, выполняющий команду. Удаляет элемент коллекции по указанному ID.
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
                collectionManager.removeById(id);
                System.out.println("Элемент коллекции удален");
            } else {
                System.out.println("Данного элемента коллекции не существует");
            }
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("Не указаны аргументы команды");
        } catch (NumberFormatException ex) {
            System.err.println("Формат аргумента не соответствует целочисленному: " + ex.getMessage());
        }
    }

    /**
     * Метод, возвращающий описание команды.
     *
     * @return строка с описанием команды, указывающая, что команда удаляет элемент с указанным ID.
     */
    @Override
    public String getDescription() {
        return "удаляет элемент с указанным ID";
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