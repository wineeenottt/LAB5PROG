package org.wineeenottt.WorkWithFile;

import org.wineeenottt.Collection.Coordinates;
import org.wineeenottt.Collection.Location;
import org.wineeenottt.Collection.Route;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс для работы с файлами, содержащими данные о маршрутах.
 * Предоставляет методы для чтения и записи данных в формате CSV.
 */
public class FileManager {


    /**
     * Парсит CSV файл и возвращает набор маршрутов.
     *
     * @param filePath путь к CSV файлу
     * @return набор маршрутов, содержащихся в файле
     * @throws IOException если произошла ошибка ввода-вывода при чтении файла
     */
    public Set<Route> parseCsvFile(String filePath) throws IOException {
        Set<Route> routes = new HashSet<>();

        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            boolean isFirstLine = true;
            String line;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] fields = line.split(",");
                if (fields.length < 14) {
                    System.err.println("Ошибка: некорректный формат строки в CSV файле");
                    continue;
                }

                try {
                    int id = Integer.parseInt(fields[0].trim());
                    String name = fields[1].trim();
                    double coordX = Double.parseDouble(fields[2].trim());
                    float coordY = Float.parseFloat(fields[3].trim());
                    ZonedDateTime creationDate = ZonedDateTime.parse(fields[4].trim());
                    float locFromX = Float.parseFloat(fields[5].trim());
                    int locFromY = Integer.parseInt(fields[6].trim());
                    double locFromZ = Double.parseDouble(fields[7].trim());
                    String locFromName = fields[8].trim();

                    float locToX = Float.parseFloat(fields[9].trim());
                    int locToY = Integer.parseInt(fields[10].trim());
                    double locToZ = Double.parseDouble(fields[11].trim());
                    String locToName = fields[12].trim();
                    long distance = Long.parseLong(fields[13].trim());

                    Coordinates coordinates = new Coordinates(coordX, coordY);
                    Location from = new Location(locFromX, locFromY, locFromZ, locFromName);
                    Location to = new Location(locToX, locToY, locToZ, locToName);
                    Route route = new Route(id, name, coordinates, creationDate, from, to, distance);

                    routes.add(route);
                } catch (Exception e) {
                    System.err.println("Ошибка при разборе строки: " + e.getMessage());
                }
            }
        }
        return routes;
    }

    /**
     * Записывает набор маршрутов в CSV файл.
     *
     * @param filePath путь к файлу, в который будут записаны данные
     * @param routes   набор маршрутов для записи
     */
    public void parseToCsv(String filePath, Set<Route> routes) {
        try (FileWriter writer = new FileWriter(filePath)) {

            writer.write("id,name,coordinateX,coordinateY,creationDate,fromX,fromY,fromZ,fromName,toX,toY,toZ,toName,distance\n");

            for (Route route : routes) {
                writer.write(route.getId() + "," +
                        route.getName() + "," +
                        route.getCoordinates().getX() + "," +
                        route.getCoordinates().getY() + "," +
                        route.getCreationDate() + "," +
                        route.getFrom().getX() + "," +
                        route.getFrom().getY() + "," +
                        route.getFrom().getZ() + "," +
                        route.getFrom().getName() + "," +
                        route.getTo().getX() + "," +
                        route.getTo().getY() + "," +
                        route.getTo().getZ() + "," +
                        route.getTo().getName() + "," +
                        route.getDistance() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    /**
     * Находит максимальный ID среди маршрутов.
     *
     * @param routes набор маршрутов
     * @return максимальный ID или -1, если набор пуст
     */
    public int findMaxId(Set<Route> routes) {
        int maxId = -1;
        for (Route route : routes) {
            if (route.getId() > maxId) {
                maxId = route.getId();
            }
        }
        return maxId;
    }
}
