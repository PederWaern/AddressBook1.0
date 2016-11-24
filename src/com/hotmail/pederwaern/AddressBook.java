package com.hotmail.pederwaern;

import java.io.*;
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
public class AddressBook {

    private Register register;
    private LoggerClass log;


    private String fileName;

    public AddressBook(String fileName)  throws IOException  {
        log = new LoggerClass();
        log.init();
        this.fileName = fileName;
        this.register = loadFromFile(fileName);

    }

    public void start() throws IOException {
        displayWelcome();
        takeInput();
        saveToFile(fileName);
        displayGoodbye();

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
            log.severeMessageToLog(e.getMessage());
            return new Register(new ArrayList<>());
        } catch (ClassNotFoundException | IOException e) {
            log.severeMessageToLog(e.getMessage());
            throw new RuntimeException(e);


        }


    }

    /**
     * Metoden sparar registret till binärfil.
     * @param name
     */
    private void saveToFile(String name){

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(name));
            out.writeObject(register);
            out.close();
        } catch (IOException i) {
            i.printStackTrace();
        }

    }

    private void displayGoodbye() {
        log.infoMessageToLog("Program exited by user");
        System.out.println("Exiting program. Goodbye...");
    }

    private void displayWelcome() {
        log.infoMessageToLog("Program started by user");
        System.out.println("Welcome!\nOptions: add\tlist\tsearch\thelp\tdelete\tquit");
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
                log.fineMessageToLog("add typed");
            } else if (isList(input)) {
                register.list();
                log.fineMessageToLog("log typed");
            } else if (isSearch(input)){
                register.search(input);
                log.fineMessageToLog("search typed");
//            } else if (isClear(input)) {
//                register.clear();
            } else if (isHelp(input)){
                log.fineMessageToLog("help typed");
                register.help();
            } else if (isQuit(input)){
                log.fineMessageToLog("quit typed");
                break;
            } else if (isDelete(input)) {
                log.fineMessageToLog("delete typed");
                register.delete(input);
            } else {
                log.fineMessageToLog("used typed invalid command");
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


}


