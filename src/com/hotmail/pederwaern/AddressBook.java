package com.hotmail.pederwaern;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
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
public class AddressBook {

    private Register register;
    private static final Logger logger = Logger.getLogger(AddressBook.class.getName());


    private String fileName;

    public AddressBook(String fileName)  throws IOException  {
        this.fileName = fileName;
        this.register = loadFromFile(fileName);

    }

    public void start() throws IOException {
        displayWelcome();
        runAutosaver();
        takeInput();
        saveToFile(fileName);
        displayGoodbye();
        logger.info("program saved before quitting...");
        //TODO Fixa till snyggare avslutning av autosave threaden. inte använda system out...?
        //TODO Logga autosave och vanlig save på olika sätt.
        System.exit(0);

    }

    /**
     * Metoden laddar reigstret från binärfil.
     * @param fileName
     */
    public Register loadFromFile(String fileName) {

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            Register register = (Register) in.readObject();
            in.close();
            return register;
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "No binary file found to loud, new empty register ", e);

            return new Register(new ArrayList<>());
        } catch (ClassNotFoundException | IOException e) {
            logger.log(Level.SEVERE, "Class not found or IO exception", e);
            throw new RuntimeException(e);


        }

    }

    /**
     * Metoden sparar registret till binärfil.
     * @param name
     */
    private synchronized void saveToFile(String name){

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(name));
            out.writeObject(register);
            out.close();
        } catch (IOException i) {
            i.printStackTrace();
        }


    }

    private void displayGoodbye() {
        logger.info("Program exited by user");
        System.out.println("Exiting program. Goodbye...");
    }

    private void displayWelcome() {
        logger.info("Program started by user");
        System.out.println("Welcome!\nOptions: add\tlist\tsearch\thelp\tdelete\tquit");
    }

    private void runAutosaver() {
        Thread autosave = new Thread(new Autosaver());
        autosave.start();
    }
    /**
     * Denna metod hanterar användarens input. Kommandon ska skrivas med små bokstäver och är case-sensitive.
     * @throws IOException
     */
    private void takeInput() throws IOException {
        BufferedReader bufferedR = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String input = bufferedR.readLine().trim();

            if (isAdd(input)) {
                register.add(input);
                logger.fine("add typed");
            } else if (isList(input)) {
                register.list();
                logger.fine("log typed");
            } else if (isSearch(input)){
                register.search(input);
                logger.fine("search typed");
//            } else if (isClear(input)) {
//                register.clear();
            } else if (isHelp(input)){
                logger.fine("help typed");
                register.help();
            } else if (isQuit(input)){
                logger.fine("quit typed");
                break;
            } else if (isDelete(input)) {
                logger.fine("delete typed");
                register.delete(input);
            } else {
                logger.fine("used typed invalid command");
                System.out.println("Invalid command");
            }
        }
        bufferedR.close();

    }

    private boolean isDelete(String input) {

        String[] arguments = input.split(" ");
        return arguments.length > 0 && arguments[0].equals(InputCommand.DELETE);
    }

    private boolean isQuit(String input) {
        return input.equals(InputCommand.QUIT);
    }

    private boolean isClear(String input) {
        return input.equals(InputCommand.CLEAR);
    }

    private boolean isSearch(String input) {
        String[] arguments = input.split(" ");
        return arguments.length > 0 && arguments[0].equals(InputCommand.SEARCH);
    }

    private boolean isList(String input) {
        return input.equals(InputCommand.LIST);
    }

    private boolean isAdd(String input) {
        return input.length() > InputCommand.ADD.length()
                && input.subSequence(0, (InputCommand.ADD.length() +1) ).equals(InputCommand.ADD + " ");
    }

    private boolean isHelp(String input) {
        return input.equals(InputCommand.HELP);
    }


    private class Autosaver implements Runnable {

        @Override
        public void run() {
            while(true) {
                saveToFile(fileName);
                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.info("Autosave done");
            }

        }
    }
}


