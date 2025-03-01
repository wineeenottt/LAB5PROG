package org.wineeenottt.Commands;

import org.wineeenottt.Collection.CollectionManager;

/**
 * Класс InfoCommand реализует интерфейс Command и представляет команду вывода информации о коллекции.
 * Команда выводит тип коллекции, дату инициализации, количество элементов и тип элементов коллекции.
 */
public class InfoCommand implements Command {

    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     * Используется для получения информации о коллекции.
     */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса InfoCommand.
     *
     * @param collectionManager объект класса CollectionManager, используемый для получения информации о коллекции.
     */
    public InfoCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Метод, выполняющий команду. Выводит информацию о коллекции, включая тип коллекции,
     * дату инициализации, количество элементов и тип элементов коллекции.
     */
    @Override
    public void execute() {
        collectionManager.infoAboutCollection();
    }

    /**
     * Метод, возвращающий описание команды.
     *
     * @return строка с описанием команды, указывающая, что команда выводит информацию о коллекции.
     */
    @Override
    public String getDescription() {
        return "получить информацию о коллекции (тип, дата инициализации, кол-во элементов, тип элементов коллекции)";
    }
}