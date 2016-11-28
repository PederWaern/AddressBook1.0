package com.hotmail.pederwaern;

import java.io.IOException;
import java.util.logging.Logger;


/**
 * Programmet AddressBook hanterar personer i en adressbok. Attributen förnamn, efternamn och emailadress ska läggas
 * till av användaren. Varje kontakt får dessutom ett unikt ID. Användaren har möjlighet att lägga till, lista och söka i
 * listan av kontakter. När programmet startar laddas en binärfil med sparad info om listan, och när programmet avslutas
 * sparas listan till samma fil. För att ladda och spara fil används Serialization.
 * @version 1.0
 * @author Peder Waern
 *
 */
public class AddressBookApp {

    private RegisterHandler regHandler;
    private CommandHandler commandHandler;
    private MessageDisplayer messageDisplayer;
    private AutoSaver autosaver;
    private Thread autosaveThread;
    private FileManager fileManager;
    private Register register;

    private static final Logger logger = Logger.getLogger(AddressBookApp.class.getName());

    public AddressBookApp(String fileName) throws IOException  {

        fileManager = new FileManager(fileName);
        register = fileManager.loadFromFile();
        regHandler = new RegisterHandler(register);
        commandHandler = new CommandHandler(regHandler);
        autosaver = new AutoSaver(regHandler.getRegister(), fileManager);
        autosaveThread = new Thread(autosaver);
        messageDisplayer = new MessageDisplayer();

    }

    public void start() throws IOException {
        messageDisplayer.displayWelcome();
        autosaveThread.start();
        commandHandler.takeInput();
        autosaver.stop();
        fileManager.saveToFile(regHandler.getRegister());
        messageDisplayer.displayGoodbye();
    }

}


