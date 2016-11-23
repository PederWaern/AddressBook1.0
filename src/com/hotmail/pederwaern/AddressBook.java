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

    private String fileName;

    public AddressBook(String fileName)  throws IOException  {
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
            return new Register(new ArrayList<>());
        } catch (ClassNotFoundException | IOException e) {
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
        System.out.println("Exiting program. Goodbye...");
    }

    private void displayWelcome() {

        System.out.println("Welcome!\nOptions: add\tlist\tsearch\tquit");
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
            } else if (isList(input)) {
                register.list();
            } else if (isSearch(input)){
                register.search(input);
            } else if (isClear(input)) {
                register.clear();
            } else if (isQuit(input)){
                break;
            }
        }
        bufferedR.close();

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

}


