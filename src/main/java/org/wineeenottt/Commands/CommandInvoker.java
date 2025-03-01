package org.wineeenottt.Commands;

import org.wineeenottt.Collection.CollectionManager;
import org.wineeenottt.Utility.RouteFieldsReader;
import org.wineeenottt.IO.UserIO;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

/**
 * Класс, через который осуществляется исполнение команд. Хранит коллекции всех существующих команд
 */
public class CommandInvoker {
    /**
     * Коллекция команд без дополнительных аргументов, которые записываются с новой строки
     */
    private HashMap<String, Command> hashMapCommands;
    /**
     * Поле, хранящее ссылку на объект класса CollectionManager
     */
    private CollectionManager collectionManager;
    /**
     * Поле, хранящее ссылку на объект класса UserIO
     */
    private UserIO userIO;
    /**
     * Поле, хранящее строку, в которой записан адрес файла, куда следует сохранять полученную коллекцию (экземпляры коллекции)
     */
    private String inputFile;
    private String inputData;
    /**
     * Поле, хранящее ссылку на объект, осуществляющий чтение полей из указанного в userIO потока ввода
     */
    private RouteFieldsReader routeFieldsReader;
    /**
     * Поле, хранящее объект класса ExecuteScript.Script
     */
    ExecuteScriptCommand.Script script;
    /**
     * Поле, хранящее список команд
     */
    ArrayList<String> commandsHistoryList = new ArrayList<>();

    /**
     * Использование файла
     * Конструктор класса. Внутри вызывается метод putCommands, добавляющий команды в коллекции команд, создается новый объект класса ExecuteScript.Script
     */
    public CommandInvoker(CollectionManager collectionManager, UserIO userIO, String inputFile, RouteFieldsReader routeFieldsReader) {
        this.collectionManager = collectionManager;
        this.userIO = userIO;
        this.inputFile = inputFile;
        this.routeFieldsReader = routeFieldsReader;
        hashMapCommands = new HashMap<>();
        this.script = new ExecuteScriptCommand.Script();
        this.putCommands();
    }

    /**
     * Использование скрипта
     * Конструктор класса. Внутри вызывается метод putCommands, инициализируется поле, в которое присваивается существующий объект класса ExecuteScript.Script
     */
    public CommandInvoker(CollectionManager collectionManager, UserIO userIO, RouteFieldsReader routeFieldsReader, ExecuteScriptCommand.Script script, String inputFile, String inputData) {
        this.collectionManager = collectionManager;
        this.userIO = userIO;
        this.routeFieldsReader = routeFieldsReader;
        routeFieldsReader.setInputData(inputData);
        this.inputFile = inputFile;
        hashMapCommands = new HashMap<>();
        this.script = script;
        this.putCommands();
        this.inputData = inputData;
    }

    /**
     * Метод, добавляющий команды в соответствующие им коллекции.
     */
    private void putCommands() {
        hashMapCommands.put("info", new InfoCommand(collectionManager));
        hashMapCommands.put("show", new ShowCommand(collectionManager));
        hashMapCommands.put("clear", new ClearCommand(collectionManager));
        hashMapCommands.put("save", new SaveCommand(collectionManager, inputFile));
        hashMapCommands.put("exit", new ExitCommand());
        hashMapCommands.put("history", new HistoryCommand(commandsHistoryList));
        hashMapCommands.put("print_field_ascending_distance", new PrintFieldAscendingDistanceCommand(collectionManager));
        hashMapCommands.put("print_ascending", new PrintAscendingCommand(collectionManager));
        hashMapCommands.put("help", new HelpCommand(hashMapCommands));
        hashMapCommands.put("sum_of_dictance", new SumOfDistanceCommand(collectionManager));
        hashMapCommands.put("add", new AddCommand(collectionManager, routeFieldsReader));
        hashMapCommands.put("add_if_max", new AddIfMaxCommand(collectionManager, routeFieldsReader, userIO));
        hashMapCommands.put("update", new UpdateElementCommand(collectionManager, userIO));
        hashMapCommands.put("remove_by_id", new RemoveByIdCommand(collectionManager));
        hashMapCommands.put("execute_script", new ExecuteScriptCommand(collectionManager, routeFieldsReader, script, inputFile, inputData));
        hashMapCommands.put("remove_greater", new RemoveGreaterCommand(collectionManager, routeFieldsReader));
    }

    /**
     * Метод, который определяет из полученной строки команду, исполняет ее и передает ей необходимые аргументы.
     * Если команда не распознана, то в стандартный поток вывода выводится соответствующее сообщение.
     */
    public void execute(String firstCommandLine) {
        String[] words = firstCommandLine.trim().split("\\s+");
        String commandKey = words[0].toLowerCase(Locale.ROOT);
        String[] args = Arrays.copyOfRange(words, 1, words.length);

        if (hashMapCommands.containsKey(commandKey)) {
            Command command = hashMapCommands.get(commandKey);
            if (command instanceof CommandWithArguments) {
                CommandWithArguments commandWithArgs = (CommandWithArguments) command;
                commandWithArgs.getCommandArguments(args);
                commandWithArgs.execute();
            } else {
                command.execute();
            }
            addToCommandsHistory(commandKey);
        } else {
            System.err.println("Команда " + words[0] + " не распознана, для получения справки введите команду help");
        }
    }

    /**
     * Метод, который добавляет команду в историю команд. Если размер списка команд достигает 11, удаляется самая старая команда, после чего добавляется новая
     */
    public void addToCommandsHistory(String string) {
        if (commandsHistoryList.size() == 11) {
            commandsHistoryList.remove(0);
        }
        commandsHistoryList.add(string);
    }
}