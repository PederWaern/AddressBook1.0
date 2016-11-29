package com.hotmail.pederwaern;

import java.io.IOException;


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

    private FileManager fileManager;
    private CommandHandler commandHandler;
    private RegisterHandler regHandler;
    private MessageDisplayer messageDisplayer;
    private Thread autosaveThread;
    private AutoSaver autosaver;

    public AddressBookApp(String fileName) throws IOException  {

        fileManager = new FileManager(fileName);
        Register register = fileManager.loadFromFile();
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


