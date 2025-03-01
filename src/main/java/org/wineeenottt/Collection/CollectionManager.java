package org.wineeenottt.Collection;

import org.wineeenottt.WorkWithFile.FileManager;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CollectionManager {

    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private int maxId;
    /**
     * Коллекция, над которой будет осуществляться работа
     */
    private final HashSet<Route> hashSetRouteCollection;
    /**
     * Время создания коллекции
     */
    private final ZonedDateTime collectionCreation;

    /**
     * Конструктор класса
     */
    public CollectionManager(Set<Route> routes) {
        this.hashSetRouteCollection = new HashSet<>(routes);
        this.collectionCreation = ZonedDateTime.now();
        this.maxId = new FileManager().findMaxId(routes);
    }

    /**
     * Метод, выводящий основную информацию о коллекции
     */
    public void infoAboutCollection() {
        System.out.println("Коллекция: " + hashSetRouteCollection.getClass().getSimpleName());
        System.out.println("Тип элементов: " + Route.class.getSimpleName());
        System.out.println("Время создания коллекции: " + collectionCreation.format(DateTimeFormatter.ofPattern(PATTERN)));
        System.out.println("Количество элементов: " + hashSetRouteCollection.size());
    }

    /**
     * Метод, выводящий информацию по элементам коллекции
     */
    public void showElementsCollection() {
        if (hashSetRouteCollection.isEmpty()) {
            System.out.println("Коллекция пуста");
        } else {
            hashSetRouteCollection.stream()
                    .sorted(Comparator.comparing(Route::getId))
                    .forEach(System.out::println);
        }
    }

    /**
     * Метод, удаляющий все элементы коллекции
     */
    public void clearAllCollection() {
        hashSetRouteCollection.clear();
    }

    /**
     * Метод, выводящий булевый результат истины, если в коллекции существует элемент с выбранным id, иначе ложь
     */
    public boolean containsIdRoute(Integer id) {
        for (Route route : hashSetRouteCollection) {
            if (route.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Метод, удаляющий из коллекции все элемента, превышающий заданный
     */
    public void removeGreater(Integer id) {
        if (hashSetRouteCollection.isEmpty()) {
            System.out.println("Коллекция пуста");
        } else {
            HashSet<Route> toRemove = new HashSet<>();

            for (Route route : hashSetRouteCollection) {
                if (route.getId() > id) {
                    toRemove.add(route);
                }
            }
            hashSetRouteCollection.removeAll(toRemove);
        }
    }

    public void showRouteSortedDistance() {
        if (hashSetRouteCollection.isEmpty()) {
            System.out.println("Коллекция пуста");
        } else {
            List<Long> distances = new ArrayList<>();
            for (Route route : hashSetRouteCollection) {
                if (route.getDistance() != null) {
                    distances.add(route.getDistance());
                }
            }
            Collections.sort(distances);
            for (Long distance : distances) {
                System.out.println(distance);
            }
        }
    }

    /**
     * Метод, сортирует элементы коллекции в порядке возрастания
     */
    public void showIdSortedCollection() {
        if (hashSetRouteCollection.isEmpty()) {
            System.out.println("Коллекция пуста");
        } else {
            hashSetRouteCollection.stream()
                    .sorted(Comparator.comparingInt(Route::getId))
                    .forEach(route -> System.out.println("ID: " + route.getId() + ", Name: " + route.getName()));
        }
    }

    public void removeById(Integer id) {
        if (hashSetRouteCollection.isEmpty()) {
            System.out.println("Коллекция пуста");
        } else {
            Route toRemove = null;
            for (Route route :  hashSetRouteCollection) {
                if (route.getId().equals(id)) {
                    toRemove = route;
                    break;
                }
            }
            if (toRemove != null) {
                hashSetRouteCollection.remove(toRemove);
            }
        }
    }

    public Long sumOfDistance() {
        if (hashSetRouteCollection.isEmpty()) {
            System.out.println("Коллекция пуста");
            return 0L;
        } else {
            Long sum = 0L;
            for (Route route : hashSetRouteCollection) {
                if (route.getDistance() != null) {
                    sum += route.getDistance();
                }
            }
            return sum;
        }
    }

    public int getMaxId() {
        return maxId;
    }


    /**
     * Добавляет новый маршрут в коллекцию, генерируя ID автоматически (maxId + 1)
     */
    public void addRoute(String name, Coordinates coordinates, ZonedDateTime creationDate, Location from, Location to, Long distance) {
        int newId = maxId + 1;
        Route route = new Route(newId, name, coordinates, creationDate, from, to, distance);
        hashSetRouteCollection.add(route);
        maxId = newId;
    }

    /**
     * Добавляет маршрут, если переданный ID больше текущего maxId, и обновляет maxId.
     * Если переданный ID не больше maxId, маршрут не добавляется.
     */
    public void addIfMaxIdRoute(int id, String name, Coordinates coordinates, ZonedDateTime creationDate, Location from, Location to, Long distance) {
        if (id > maxId) {
            Route route = new Route(id, name, coordinates, creationDate, from, to, distance);
            hashSetRouteCollection.add(route);
            maxId = id;
        }
    }

    public void save(String filePath) {
        FileManager csvParser = new FileManager();
        csvParser.parseToCsv(filePath, hashSetRouteCollection);
    }
    public void update(Integer id, String field, String value) {
        try {
            for (Route route : hashSetRouteCollection) {
                if (route.getId().equals(id)) {
                    switch (field) {
                        case "Name":
                            route.setName(validateString(value));
                            break;
                        case "CoordinateX":
                            route.setCoordinateX(parseDouble(value));
                            break;
                        case "CoordinateY":
                            route.setCoordinateY(parseFloat(value));
                            break;
                        case "LocationFromX":
                            route.getFrom().setX(parseFloat(value));
                            break;
                        case "LocationFromY":
                            route.getFrom().setY(parseInteger(value));
                            break;
                        case "LocationFromZ":
                            route.getFrom().setZ(parseDouble(value));
                            break;
                        case "LocationFromName":
                            route.getFrom().setName(validateNullableString(value));
                            break;
                        case "LocationToX":
                            route.getTo().setX(parseFloat(value));
                            break;
                        case "LocationToY":
                            route.getTo().setY(parseInteger(value));
                            break;
                        case "LocationToZ":
                            route.getTo().setZ(parseDouble(value));
                            break;
                        case "LocationToName":
                            route.getTo().setName(validateNullableString(value));
                            break;
                        case "Distance":
                            route.setDistance(parseLong(value));
                            break;
                        case "Stop":
                            return;
                        default:
                            System.out.println("Поле не распознано (если вы написали stop, то изменение завершено)");
                            return;
                    }
                    System.out.println("Значение поля было изменено");
                    return;
                }
            }
            System.out.println("Маршрут с ID " + id + " не найден.");
        } catch (NumberFormatException ex) {
            System.err.println("Ошибка: Неверный формат числа (" + ex.getMessage() + ")");
        } catch (NullPointerException ex) {
            System.err.println("Ошибка: Значение не может быть пустым");
        }
    }
    public String getFieldNames() {
        return "Список всех полей: \n" +
                "Name (String)\n" +
                "CoordinateX (Double)\n" +
                "CoordinateY (Float)\n" +
                "LocationFromX (Float)\n" +
                "LocationFromY (Integer)\n" +
                "LocationFromZ (Double)\n" +
                "LocationFromName (String)\n" +
                "LocationToX (Float)\n" +
                "LocationToY (Integer)\n" +
                "LocationToZ (Double)\n" +
                "LocationToName (String)\n" +
                "Distance (Long)\n";
    }

    private String validateString(String value) {
        if (value.isEmpty()) throw new NullPointerException("Значение не может быть пустым");
        return value;
    }

    private String validateNullableString(String value) {
        return value.isEmpty() ? null : value;
    }

    private Double parseDouble(String value) {
        return value.isEmpty() ? null : Double.parseDouble(value);
    }

    private Float parseFloat(String value) {
        return value.isEmpty() ? 0f : Float.parseFloat(value);
    }

    private Integer parseInteger(String value) {
        return value.isEmpty() ? 0 : Integer.parseInt(value);
    }

    private Long parseLong(String value) {
        if (value.isEmpty()) throw new NullPointerException("Значение не может быть пустым");
        return Long.parseLong(value);
    }
}