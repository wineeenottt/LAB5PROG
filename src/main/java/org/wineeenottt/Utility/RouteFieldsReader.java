package org.wineeenottt.Utility;

import org.wineeenottt.Collection.CollectionManager;
import org.wineeenottt.Collection.Coordinates;
import org.wineeenottt.Collection.Location;
import org.wineeenottt.Exceptions.ValidValuesRangeException;
import org.wineeenottt.IO.UserIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RouteFieldsReader {

    private final UserIO userIO;
    private CollectionManager collectionManager;
    private Scanner scanner;
    private String[] inputDataArray;
    private int inputIndex = 0;

    public RouteFieldsReader(UserIO userIO, CollectionManager collectionManager) {
        this.userIO = userIO;
        this.collectionManager = collectionManager;
    }

    public void setInputData(String fileName) {
        try {
            scanner = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("Не удалось открыть файл: " + fileName);
            scanner = null;
        }
        inputDataArray = null;
        inputIndex = 0;
    }

    private String readNextValue(String val) {
        while ((inputDataArray == null || inputIndex >= inputDataArray.length) && scanner != null && scanner.hasNextLine()) {
            inputDataArray = scanner.nextLine().trim().split(",");
            inputIndex = 0;
        }

        if (inputDataArray != null && inputIndex < inputDataArray.length) {
            return inputDataArray[inputIndex++].trim();
        }

        userIO.printCommandText(val);
        return userIO.readLine().trim();
    }

    public void clearInputData(){
        scanner = null;
    }

    public String readName() {
        while (true) {
            String str = readNextValue("Name (not null): ");
            if (!str.isEmpty()) return str;
            userIO.printCommandError("Значение поля не может быть null или пустой строкой\n");
        }
    }

    public Coordinates readCoordinates() {
        return new Coordinates(readCoordinateX(), readCoordinateY());
    }

    public Double readCoordinateX() {
        while (true) {
            try {
                double x = Double.parseDouble(readNextValue("CoordinateX (Double & x <= 750): "));
                if (x > 750) throw new ValidValuesRangeException();
                return x;
            } catch (ValidValuesRangeException e) {
                System.out.println("Координата x имеет максимальное значение - 750");
            } catch (NumberFormatException e) {
                System.err.println("Число должно быть типа Double");
            }
        }
    }

    public float readCoordinateY() {
        return readFloat("CoordinateY (Float): ");
    }

    public Location readLocation() {
        return new Location(readLocationCoordinateX(), readLocationCoordinateY(), readLocationCoordinateZ(), readLocationName());
    }

    public float readLocationCoordinateX() {
        return readFloat("Location coordinateX (Float): ");
    }

    public int readLocationCoordinateY() {
        return readInt("Location coordinateY (Int): ");
    }

    public double readLocationCoordinateZ() {
        return readDouble("Location coordinateZ (Double): ");
    }

    public String readLocationName() {
        while (true) {
            String str = readNextValue("LocationName (not null): ");
            if (!str.isEmpty()) return str;
            userIO.printCommandError("Значение поля не может быть null или пустой строкой\n");
        }
    }

    public long readDistance() {
        while (true) {
            try {
                long distance = Long.parseLong(readNextValue("Distance (Long > 1): "));
                if (distance <= 1) throw new ValidValuesRangeException();
                return distance;
            } catch (ValidValuesRangeException e) {
                System.out.println("Distance должно быть больше 1");
            } catch (NumberFormatException e) {
                System.err.println("Число должно быть типа Long");
            }
        }
    }

    private float readFloat(String val) {
        while (true) {
            try {
                return Float.parseFloat(readNextValue(val));
            } catch (NumberFormatException e) {
                System.err.println("Ввод должен быть типа Float");
            }
        }
    }

    private double readDouble(String val) {
        while (true) {
            try {
                return Double.parseDouble(readNextValue(val));
            } catch (NumberFormatException e) {
                System.err.println("Ввод должен быть типа Double");
            }
        }
    }

    public int readInt(String val) {
        while (true) {
            try {
                return Integer.parseInt(readNextValue(val));
            } catch (NumberFormatException e) {
                System.err.println("Ввод должен быть типа Integer");
            }
        }
    }
}
