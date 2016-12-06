package com.hotmail.pederwaern.AddressBookApp;

public class AddressBookApp {

    private final String dataFileName = "register.data";

    private FileManager fileManager;
    private CommandHandler commandHandler;
    private RegisterHandler regHandler;
    private MessageDisplayer messageDisplayer;
    private Thread autosaveThread;
    private AutoSaver autosaver;
    private Register localRegister;

    public AddressBookApp()  {
        fileManager = new FileManager(dataFileName);
        localRegister = fileManager.loadFromFile();
        regHandler = new RegisterHandler(localRegister);
        commandHandler = new CommandHandler(regHandler);
        autosaver = new AutoSaver(regHandler.getLocalRegister(), fileManager);
        autosaveThread = new Thread(autosaver);
        messageDisplayer = new MessageDisplayer();
    }

    public void start(){
        autosaveThread.start();
        messageDisplayer.displayWelcome();
        regHandler.loadRemoteRegister("server one", ConnectionInfo.SERVER_ONE_PORT);
        regHandler.loadRemoteRegister("server two", ConnectionInfo.SERVER_TWO_PORT);
        commandHandler.takeInput();
        autosaver.stop();
        fileManager.saveToFile(regHandler.getLocalRegister());
        messageDisplayer.displayGoodbye();
        System.exit(0);
    }
}

