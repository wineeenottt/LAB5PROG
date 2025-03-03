package org.wineeenottt.Commands;

import org.wineeenottt.Collection.CollectionManager;
import org.wineeenottt.Utility.RouteFieldsReader;
import org.wineeenottt.IO.UserIO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

/**
 * Класс CommandInvoker отвечает за вызов и выполнение команд, управляющих коллекцией маршрутов.
 * Он хранит коллекцию команд, историю выполненных команд и обеспечивает их выполнение.
 */
public class CommandInvoker {

    /**
     * Коллекция команд, которые не требуют дополнительных аргументов и записываются с новой строки.
     */
    private final HashMap<String, Command> hashMapCommands;

    /**
     * Ссылка на объект класса CollectionManager, который управляет коллекцией маршрутов.
     */
    private final CollectionManager collectionManager;

    /**
     * Ссылка на объект класса UserIO, который обеспечивает взаимодействие с пользователем.
     */
    private final UserIO userIO;

    /**
     * Строка, содержащая адрес файла, куда следует сохранять коллекцию.
     */
    private final String inputFile;

    /**
     * Строка, содержащая входные данные для выполнения команды add.
     */
    private String inputData;

    /**
     * Ссылка на объект, который читает поля маршрута из указанного потока ввода.
     */
    private final RouteFieldsReader routeFieldsReader;

    /**
     * Объект класса ExecuteScript.Script, который управляет выполнением скриптов.
     */
    ExecuteScriptCommand.Script script;

    /**
     * Список, хранящий историю выполненных команд.
     */
    ArrayList<String> commandsHistoryList = new ArrayList<>();

    /**
     * Конструктор класса CommandInvoker, используемый при работе с файлом.
     * Инициализирует поля и добавляет команды в коллекцию команд.
     *
     * @param collectionManager Объект класса CollectionManager.
     * @param userIO            Объект класса UserIO.
     * @param inputFile         Адрес файла для сохранения коллекции.
     * @param routeFieldsReader Объект класса RouteFieldsReader.
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
     * Конструктор класса CommandInvoker, используемый при работе со скриптом.
     * Инициализирует поля и добавляет команды в коллекцию команд.
     *
     * @param collectionManager Объект класса CollectionManager.
     * @param userIO            Объект класса UserIO.
     * @param routeFieldsReader Объект класса RouteFieldsReader.
     * @param script            Объект класса ExecuteScript.Script.
     * @param inputFile         Адрес файла для сохранения коллекции.
     * @param inputData         Входные данные для выполнения команды add.
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
     * Метод, добавляющий команды в коллекцию команд.
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
        hashMapCommands.put("sum_of_distance", new SumOfDistanceCommand(collectionManager));
        hashMapCommands.put("add", new AddCommand(collectionManager, routeFieldsReader));
        hashMapCommands.put("add_if_max", new AddIfMaxCommand(collectionManager, routeFieldsReader, userIO));
        hashMapCommands.put("update", new UpdateElementCommand(collectionManager, userIO));
        hashMapCommands.put("remove_by_id", new RemoveByIdCommand(collectionManager));
        hashMapCommands.put("execute_script", new ExecuteScriptCommand(collectionManager, routeFieldsReader, script, inputFile, inputData));
        hashMapCommands.put("remove_greater", new RemoveGreaterCommand(collectionManager));
    }

    /**
     * Метод, который определяет команду из полученной строки, выполняет её и передает необходимые аргументы.
     * Если команда не распознана, выводится соответствующее сообщение.
     *
     * @param firstCommandLine Строка, содержащая команду и её аргументы.
     */
    public void execute(String firstCommandLine) {
        String[] words = firstCommandLine.trim().split("\\s+");
        String commandKey = words[0].toLowerCase(Locale.ROOT);
        String[] args = Arrays.copyOfRange(words, 1, words.length);

        boolean isScriptExecution = (inputData != null);

        if (hashMapCommands.containsKey(commandKey)) {
            Command command = hashMapCommands.get(commandKey);

            if (command instanceof CommandWithArguments commandWithArgs) {
                commandWithArgs.getCommandArguments(args);

                if (command instanceof UpdateElementCommand updateCommand) {
                    updateCommand.setIsScriptExecution(isScriptExecution);
                }

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
     * Метод, добавляющий команду в историю команд.
     * Если размер списка команд достигает 11, удаляется самая старая команда, после чего добавляется новая.
     *
     * @param string Команда, которую необходимо добавить в историю.
     */
    public void addToCommandsHistory(String string) {
        if (commandsHistoryList.size() == 11) {
            commandsHistoryList.remove(0);
        }
        commandsHistoryList.add(string);
    }
}