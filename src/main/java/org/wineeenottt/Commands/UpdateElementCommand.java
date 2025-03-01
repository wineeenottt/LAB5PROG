package org.wineeenottt.Commands;

import org.wineeenottt.Collection.CollectionManager;
import org.wineeenottt.IO.UserIO;


public class UpdateElementCommand implements CommandWithArguments {

    private CollectionManager collectionManager;

    private UserIO userIO;

    private String[] commandArguments;


    public UpdateElementCommand(CollectionManager collectionManager, UserIO userIO) {
        this.collectionManager = collectionManager;
        this.userIO = userIO;
    }


    @Override
    public void execute() {
        try {
            if (collectionManager.containsIdRoute(Integer.parseInt(commandArguments[0]))) {
                userIO.printCommandText(collectionManager.getFieldNames());

                userIO.printCommandText("Напишите stop, если хотите прервать изменение элемента коллекции\n");
                userIO.printCommandText("Введите значения полей для элемента коллекции:\n");
                String[] commandWords = new String[0];
                do {
                    try {
                        commandWords = userIO.readLine().trim().split("\\s+");
                        if (commandWords.length == 1) {
                            collectionManager.update(Integer.parseInt(commandArguments[0]), commandWords[0], "");//пустая строка в поле
                        } else
                            collectionManager.update(Integer.parseInt(commandArguments[0]), commandWords[0], commandWords[1]);
                    } catch (IndexOutOfBoundsException ex) {
                        System.err.println("Не указано поле/значение");
                    }
                } while (!commandWords[0].equals("stop"));
            } else System.err.println("Элемента с данным id в коллекции не существует");
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("Не указаны все аргументы команды");
        } catch (NumberFormatException ex) {
            System.err.println("Формат аргумента не соответствует целочисленному " + ex.getMessage());
        }
    }


    @Override
    public String getDescription() {
        return "изменяет указанное поле выбранного по ID элемента коллекции";
    }


    @Override
    public void getCommandArguments(String[] commandArguments) {
        this.commandArguments = commandArguments;
    }
}


