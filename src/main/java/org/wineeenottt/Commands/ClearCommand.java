package org.wineeenottt.Commands;

import org.wineeenottt.Collection.CollectionManager;

/**
 * Класс ClearCommand реализует интерфейс Command и представляет команду очистки коллекции.
 * Команда удаляет все элементы из коллекции и выводит соответствующее сообщение.
 */
public class ClearCommand implements Command {

    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     * Используется для доступа к методам управления коллекцией.
     */
    private CollectionManager collectionManager;

    /**
     * Конструктор класса ClearCommand.
     *
     * @param collectionManager объект класса CollectionManager, используемый для управления коллекцией.
     */
    public ClearCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Метод, выполняющий команду. Очищает коллекцию и выводит сообщение об успешной очистке.
     */
    @Override
    public void execute() {
        collectionManager.clearAllCollection();
        System.out.println("Коллекция очищена");
    }

    /**
     * Метод, возвращающий описание команды.
     *
     * @return строка с описанием команды, указывающая, что команда очищает все элементы коллекции.
     */
    @Override
    public String getDescription() {
        return "очищает все элементы коллекции";
    }
}