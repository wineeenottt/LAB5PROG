package org.wineeenottt.Commands;

import org.wineeenottt.Collection.CollectionManager;

/**
 * Класс PrintFieldAscendingDistanceCommand реализует интерфейс Command и представляет команду,
 * которая выводит значения поля distance всех элементов коллекции в порядке возрастания.
 */
public class PrintFieldAscendingDistanceCommand implements Command {

    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     * Используется для доступа к методам управления коллекцией и получения данных о расстояниях.
     */
    private CollectionManager collectionManager;

    /**
     * Конструктор класса PrintFieldAscendingDistanceCommand.
     *
     * @param collectionManager объект класса CollectionManager, используемый для управления коллекцией.
     */
    public PrintFieldAscendingDistanceCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Метод, выполняющий команду. Выводит значения поля distance всех элементов коллекции
     * в порядке возрастания. Если коллекция пуста, выводится соответствующее сообщение.
     */
    @Override
    public void execute() {
        collectionManager.showRouteSortedDistance();
    }

    /**
     * Метод, возвращающий описание команды.
     *
     * @return строка с описанием команды, указывающая, что команда выводит значения поля distance
     * всех элементов коллекции в порядке возрастания.
     */
    @Override
    public String getDescription() {
        return "выводит значения поля distance всех элементов в порядке возрастания";
    }
}