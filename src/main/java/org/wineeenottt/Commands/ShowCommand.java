package org.wineeenottt.Commands;

import org.wineeenottt.Collection.CollectionManager;

/**
 * Класс ShowCommand реализует интерфейс Command и представляет команду,
 * которая отображает подробное содержимое всех элементов коллекции.
 */
public class ShowCommand implements Command {

    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     * Используется для доступа к методам управления коллекцией и отображения её элементов.
     */
    private CollectionManager collectionManager;

    /**
     * Конструктор класса ShowCommand.
     *
     * @param collectionManager объект класса CollectionManager, используемый для управления коллекцией.
     */
    public ShowCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Метод, выполняющий команду. Отображает подробное содержимое всех элементов коллекции.
     * Если коллекция пуста, выводится соответствующее сообщение.
     */
    @Override
    public void execute() {
        collectionManager.showElementsCollection();
    }

    /**
     * Метод, возвращающий описание команды.
     *
     * @return строка с описанием команды, указывающая, что команда отображает подробное содержимое всех элементов коллекции.
     */
    @Override
    public String getDescription() {
        return "показывает подробное содержимое всех элементов коллекции";
    }
}