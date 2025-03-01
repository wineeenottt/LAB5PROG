package org.wineeenottt.Commands;

import org.wineeenottt.Collection.CollectionManager;
import org.wineeenottt.Exceptions.RecoursiveCallException;
import org.wineeenottt.Utility.RouteFieldsReader;
import org.wineeenottt.IO.UserIO;


import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Класс, управляющий выполнением скрипта
 */
public class ExecuteScriptCommand implements CommandWithArguments {
    /**
     * Массив, хранящий аргументы команды
     */
    private String[] commandArguments;
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager
     */
    private CollectionManager collectionManager;
    /**
     * Поле, хранящее ссылку на объект класса UserIO
     */
    private UserIO userIO;
    /**
     * Поле, хранящее ссылку на объект, осуществляющий чтение полей из указанного в userIO потока ввода
     */
    private RouteFieldsReader routeFieldsReader;
    /**
     * Поле, хранящее адрес файла, из которого следует исполнять скрипт
     */
    private String scriptPath;
    /**
     * Поле, хранящее объект класса ExecuteScript.Script
     */
    private Script script;

    private String inputFile;
    private String inputData;

    /**
     * Конструктор класса
     */
    public ExecuteScriptCommand(CollectionManager collectionManager, RouteFieldsReader routeFieldsReader, Script script, String inputFile, String inputData) {
        this.collectionManager = collectionManager;
        this.routeFieldsReader = routeFieldsReader;
        this.script = script;
        this.inputFile = inputFile;
        this.inputData = inputData;
    }

    /**
     * Метод, исполняющий команду.
     * В коллекцию scripts при начале исполнения добавляется адрес скрипта, далее идет само его исполнение, в конце адрес файла удаляется. В случае ошибки выводится соответствующее сообщение
     */
    @Override
    public void execute() {
        try {
            if (commandArguments.length != 2) {
                throw new IllegalArgumentException("Скрипт не передан в качестве аргумента команды, либо количество аргументов больше 2");
            }

            scriptPath = commandArguments[0];

            if (script.scriptPaths.contains(scriptPath)) {
                throw new RecoursiveCallException("Скрипт " + scriptPath + " уже выполняется (Рекурсивный вызов)");
            }
            script.putScript(scriptPath);

            File ioFile = new File(scriptPath);
            if (!ioFile.exists() || !ioFile.isFile() || !ioFile.canRead()) {
                throw new IOException("Файл скрипта недоступен для чтения");
            }

            String dataPath = commandArguments[1];
            File dataFile = new File(dataPath);
            if (!dataFile.exists() || !dataFile.isFile() || !dataFile.canRead()) {
                throw new IOException("Файл данных недоступен для чтения");
            }

            try (FileInputStream fileInputStream = new FileInputStream(ioFile);
                 InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                 Scanner scanner = new Scanner(inputStreamReader)) {

                userIO = new UserIO(scanner);
                CommandInvoker commandInvoker = new CommandInvoker(collectionManager, userIO, routeFieldsReader, script, inputFile, dataPath);

                while (scanner.hasNext()) {
                    commandInvoker.execute(scanner.nextLine().trim());
                }
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Ошибка: Файл скрипта не найден");
        } catch (IOException | IllegalArgumentException | RecoursiveCallException ex) {
            System.err.println("Ошибка: " + ex.getMessage());
        } finally {
            script.removeScript(scriptPath);
        }
    }

    /**
     * Список аргументов команды
     */
    @Override
    public void getCommandArguments(String[] commandArguments) {
        this.commandArguments = commandArguments;
    }

    /**
     * Описание команды execute_script
     */
    @Override
    public String getDescription() {
        return " считать и исполнить скрипт из указанного файла";
    }

    /**
     * Статический класс, в котором хранится коллекция адресов скриптов
     */
    static class Script {
        /**
         * Коллекция, в которой хранятся адреса запущенных скриптов
         */
        private ArrayList<String> scriptPaths = new ArrayList<String>();

        /**
         * Метод, добавляющий скрипт в коллекцию
         */
        public void putScript(String scriptPath) {
            scriptPaths.add(scriptPath);
        }

        /**
         * Метод, убирающий скрипт из коллекции
         */
        public void removeScript(String scriptPath) {
            scriptPaths.remove(scriptPath);
        }
    }
}

