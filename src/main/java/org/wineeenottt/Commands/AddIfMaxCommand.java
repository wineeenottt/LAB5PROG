package org.wineeenottt.Commands;

import org.wineeenottt.Collection.CollectionManager;
import org.wineeenottt.Collection.Route;
import org.wineeenottt.Utility.RouteFieldsReader;
import org.wineeenottt.IO.UserIO;

import java.time.ZonedDateTime;

/**
 * Класс AddIfMaxCommand реализует интерфейс Command и представляет команду добавления нового элемента в коллекцию,
 * если его ID больше максимального ID существующих элементов в коллекции.
 * Команда использует RouteFieldsReader для чтения данных о маршруте и UserIO для взаимодействия с пользователем.
 */
public class AddIfMaxCommand implements Command {

    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     * Используется для управления коллекцией и проверки условий добавления элемента.
     */
    private final CollectionManager collectionManager;

    /**
     * Поле, хранящее ссылку на объект RouteFieldsReader.
     * Используется для чтения данных о маршруте из указанного потока ввода.
     */
    private final RouteFieldsReader routeFieldsReader;

    /**
     * Поле, хранящее ссылку на объект UserIO.
     * Используется для взаимодействия с пользователем (вывод ошибок и запросов).
     */
    private final UserIO userIO;

    /**
     * Конструктор класса AddIfMaxCommand.
     *
     * @param collectionManager объект класса CollectionManager, используемый для управления коллекцией.
     * @param routeFieldsReader объект класса RouteFieldsReader, используемый для чтения данных о маршруте.
     * @param userIO объект класса UserIO, используемый для взаимодействия с пользователем.
     */
    public AddIfMaxCommand(CollectionManager collectionManager, RouteFieldsReader routeFieldsReader, UserIO userIO) {
        this.collectionManager = collectionManager;
        this.routeFieldsReader = routeFieldsReader;
        this.userIO = userIO;
    }

    /**
     * Метод, выполняющий команду. Проверяет, существует ли элемент с указанным ID,
     * и добавляет новый элемент в коллекцию, если его ID больше максимального ID существующих элементов.
     * В случае ошибки (например, если ID уже существует или меньше максимального) выводит соответствующее сообщение.
     */
    @Override
    public void execute() {
        int id = routeFieldsReader.readInt("ID (Integer > 0): ");

        if (collectionManager.containsIdRoute(id)) {
            userIO.printCommandError("Маршрут с таким ID уже существует");
            return;
        }

        if (id <= collectionManager.getMaxId()) {
            userIO.printCommandError("ID должен быть больше максимального существующего ID");
            return;
        }

        Route newRoute = new Route(
                id,
                routeFieldsReader.readName(),
                routeFieldsReader.readCoordinates(),
                ZonedDateTime.now(),
                routeFieldsReader.readLocation(),
                routeFieldsReader.readLocation(),
                routeFieldsReader.readDistance()
        );

        collectionManager.addIfMaxIdRoute(id, newRoute.getName(), newRoute.getCoordinates(), newRoute.getCreationDate(), newRoute.getFrom(), newRoute.getTo(), newRoute.getDistance());
        System.out.println("Маршрут добавлен");

        routeFieldsReader.clearInputData();
    }

    /**
     * Метод, возвращающий описание команды.
     *
     * @return строка с описанием команды, указывающая, что команда добавляет элемент в коллекцию,
     * если его ID больше максимального ID существующих элементов.
     */
    @Override
    public String getDescription() {
        return "добавляет элемент в коллекцию, если его ID больше максимального ID существующих элементов";
    }
}