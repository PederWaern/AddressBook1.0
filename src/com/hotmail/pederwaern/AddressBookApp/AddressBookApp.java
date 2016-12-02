package com.hotmail.pederwaern.AddressBookApp;

import java.util.ArrayList;

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
    private RemoteContactHandler remoteContactHandler;
    private Thread remoteThread;

    private ArrayList<Contact> remoteContacts = new ArrayList<>();

    public AddressBookApp(String fileName)  {

        fileManager = new FileManager(fileName);
        Register localRegister = fileManager.loadFromFile();
        regHandler = new RegisterHandler(localRegister);
        commandHandler = new CommandHandler(regHandler);

        remoteContactHandler = new RemoteContactHandler();
        remoteThread = new Thread(remoteContactHandler);

        autosaver = new AutoSaver(regHandler.getLocalRegister(), fileManager);
        autosaveThread = new Thread(autosaver);
        messageDisplayer = new MessageDisplayer();




    }

    public void start(){

        autosaveThread.start();
        messageDisplayer.displayWelcome();
        commandHandler.takeInput();
        remoteThread.start();
        autosaver.stop();
        fileManager.saveToFile(regHandler.getLocalRegister());
        messageDisplayer.displayGoodbye();
        System.exit(0);
    }
}


