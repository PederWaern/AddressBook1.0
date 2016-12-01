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

        remoteContactHandler = new RemoteContactHandler();
        remoteThread = new Thread(remoteContactHandler);
        remoteThread.start();
        Register remoteRegister = new Register(remoteContactHandler.getRemoteContacts());
        remoteThread.stop();
        regHandler = new RegisterHandler(localRegister, remoteRegister);

        commandHandler = new CommandHandler(regHandler);
        autosaver = new AutoSaver(regHandler.getRegister(), fileManager);
        autosaveThread = new Thread(autosaver);
        messageDisplayer = new MessageDisplayer();




    }

    public void start(){

        //remotecontacs added


        messageDisplayer.displayWelcome();
        autosaveThread.start();
        commandHandler.takeInput();
        autosaver.stop();
        fileManager.saveToFile(regHandler.getRegister());
        messageDisplayer.displayGoodbye();
        System.exit(0);
    }
}


