package org.wineeenottt.Commands;

import org.wineeenottt.Collection.CollectionManager;
import org.wineeenottt.Utility.RouteFieldsReader;

/**
 * Класс AddCommand реализует интерфейс Command и представляет команду добавления нового элемента в коллекцию.
 * Команда использует RouteFieldsReader для чтения данных о маршруте и добавляет новый элемент в коллекцию через CollectionManager.
 */
public class AddCommand implements Command {

    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     * Используется для управления коллекцией и добавления нового элемента.
     */
    private CollectionManager collectionManager;

    /**
     * Поле, хранящее ссылку на объект RouteFieldsReader.
     * Используется для чтения данных о маршруте из указанного потока ввода.
     */
    private RouteFieldsReader routeFieldsReader;

    /**
     * Конструктор класса AddCommand.
     *
     * @param collectionManager объект класса CollectionManager, используемый для управления коллекцией.
     * @param routeFieldsReader объект класса RouteFieldsReader, используемый для чтения данных о маршруте.
     */
    public AddCommand(CollectionManager collectionManager, RouteFieldsReader routeFieldsReader) {
        this.collectionManager = collectionManager;
        this.routeFieldsReader = routeFieldsReader;
    }

    /**
     * Метод, выполняющий команду. Читает данные о маршруте с помощью RouteFieldsReader
     * и добавляет новый элемент в коллекцию через CollectionManager.
     * После успешного добавления выводит сообщение об успешном выполнении.
     */
    @Override
    public void execute() {
        collectionManager.addRoute(
                routeFieldsReader.readName(),
                routeFieldsReader.readCoordinates(),
                java.time.ZonedDateTime.now(),
                routeFieldsReader.readLocation(),
                routeFieldsReader.readLocation(),
                routeFieldsReader.readDistance()
        );
        System.out.println("Маршрут добавлен");

        routeFieldsReader.clearInputData();
    }

    /**
     * Метод, возвращающий описание команды.
     *
     * @return строка с описанием команды, указывающая, что команда добавляет элемент в коллекцию.
     */
    @Override
    public String getDescription() {
        return "добавляет элемент в коллекцию";
    }
}