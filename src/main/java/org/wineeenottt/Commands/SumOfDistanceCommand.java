package org.wineeenottt.Commands;

import org.wineeenottt.Collection.CollectionManager;

/**
 * Класс SumOfDistanceCommand реализует интерфейс Command и представляет команду,
 * которая вычисляет и выводит сумму значений поля distance для всех элементов коллекции.
 */
public class SumOfDistanceCommand implements Command {

    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     * Используется для доступа к методам управления коллекцией и вычисления суммы расстояний.
     */
    private final CollectionManager collectionManager;

    /**
     * Конструктор класса SumOfDistanceCommand.
     *
     * @param collectionManager объект класса CollectionManager, используемый для управления коллекцией.
     */
    public SumOfDistanceCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    /**
     * Метод, выполняющий команду. Вычисляет сумму значений поля distance для всех элементов коллекции
     * и выводит результат на экран.
     */
    @Override
    public void execute() {
        Long sum = collectionManager.sumOfDistance();
        System.out.println("Сумма всех расстояний: " + sum);
    }

    /**
     * Метод, возвращающий описание команды.
     *
     * @return строка с описанием команды, указывающая, что команда выводит сумму значений поля distance
     * для всех элементов коллекции.
     */
    @Override
    public String getDescription() {
        return "выводит сумму значений поля distance для всех элементов коллекции";
    }
}