package org.wineeenottt.Run;

import org.wineeenottt.Collection.CollectionManager;
import org.wineeenottt.Collection.Route;
import org.wineeenottt.Commands.CommandInvoker;
import org.wineeenottt.IO.UserIO;
import org.wineeenottt.Utility.RouteFieldsReader;
import org.wineeenottt.WorkWithFile.FileManager;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Класс Application является основным классом программы, который управляет запуском и выполнением команд.
 * Он отвечает за инициализацию всех необходимых компонентов, таких как менеджер коллекций, парсер CSV,
 * ввод/вывод данных и обработчик команд.
 */
public class Application {

    /**
     * Менеджер коллекций, управляющий данными коллекции маршрутов.
     */
    private CollectionManager collectionManager;

    /**
     * Парсер CSV, используемый для чтения данных из файла.
     */
    private FileManager csvParser;

    /**
     * Объект для ввода/вывода данных, взаимодействующий с пользователем.
     */
    private UserIO userIO;

    /**
     * Обработчик команд, выполняющий команды, введенные пользователем.
     */
    private CommandInvoker commandInvoker;

    /**
     * Объект для чтения полей класса Route.
     */
    private RouteFieldsReader routeFieldsReader;

    /**
     * Метод, выполняющий запуск программы. Инициализирует необходимые компоненты и загружает данные из файла.
     *
     * @param inputFile путь к файлу, из которого будут загружены данные.
     */
    public void start(String inputFile) {
        csvParser = new FileManager();
        userIO = new UserIO();

        Set<Route> routes;
        try {
            File ioFile = new File(inputFile);
            if (!ioFile.exists() || ioFile.isDirectory() || !ioFile.canRead()) {
                throw new IOException("Ошибка с файлом");
            }

            routes = csvParser.parseCsvFile(inputFile);

            collectionManager = new CollectionManager(routes);

            userIO.printCommandText("Элементы коллекции из указанного файла были загружены\n");
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            return;
        }

        routeFieldsReader = new RouteFieldsReader(userIO, collectionManager);
        commandInvoker = new CommandInvoker(collectionManager, userIO, inputFile, routeFieldsReader);


        try {
            cycle();

        } catch (NoSuchElementException ex) {
            System.err.println("Ошибка ввода: " + ex.getMessage());
        }
    }

    /**
     * Метод, выполняющий циклическое чтение команд из строки ввода.
     * Программа продолжает выполнение, пока пользователь не завершит её вводом соответствующей команды.
     */
    public void cycle() {
        userIO.printCommandText("Программа запущена\n");
        commandInvoker.execute("help");
        while (true) {
            userIO.printCommandText("\nВведите название команды:)\n");
            userIO.printPreamble();
            String line = userIO.readLine();
            commandInvoker.execute(line);
        }
    }
}