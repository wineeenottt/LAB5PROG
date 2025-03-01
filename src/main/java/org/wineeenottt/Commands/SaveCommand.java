package org.wineeenottt.Commands;

import org.wineeenottt.Collection.CollectionManager;

/**
 * Класс SaveCommand реализует интерфейс Command и представляет команду,
 * которая сохраняет коллекцию в указанный файл.
 */
public class SaveCommand implements Command {

    /**
     * Поле, хранящее адрес файла, куда следует сохранить коллекцию.
     */
    private String inputFile;

    /**
     * Поле, хранящее ссылку на объект класса CollectionManager.
     * Используется для доступа к методам управления коллекцией и её сохранения.
     */
    private CollectionManager collectionManager;

    /**
     * Конструктор класса SaveCommand.
     *
     * @param collectionManager объект класса CollectionManager, используемый для управления коллекцией.
     * @param inputFile         строка, содержащая адрес файла, куда следует сохранить коллекцию.
     */
    public SaveCommand(CollectionManager collectionManager, String inputFile) {
        this.collectionManager = collectionManager;
        this.inputFile = inputFile;
    }

    /**
     * Метод, выполняющий команду. Сохраняет коллекцию в указанный файл.
     * В случае успешного сохранения выводит сообщение об успешном выполнении.
     * В случае ошибки выводит соответствующее сообщение.
     */
    @Override
    public void execute() {
        collectionManager.save(inputFile);
        System.out.println("Коллекция была сохранена");
    }

    /**
     * Метод, возвращающий описание команды.
     *
     * @return строка с описанием команды, указывающая, что команда сохраняет коллекцию в указанный файл.
     */
    @Override
    public String getDescription() {
        return "сохраняет коллекцию в указанный файл";
    }
}