package com.hotmail.pederwaern;


import java.io.*;

/**
 * Programmet AddressBook hanterar personer i en adressbok. Attributen förnamn, efternamn och emailadress ska läggas
 * tillav användaren. Varje kontakt får dessutom ett unikt ID. Användaren har möjlighet att lägga till, lista och söka i
 * listan av kontakter. När programmet startar laddas en binärfil med sparad info om listan, och när programmet avslutas
 * sparas listan till samma fil. För att ladda och spara fil används Serialization.
 * @version 1.0
 * @author Peder Waern
 *
 */
public class AddressBook {

    private Register register;

    public AddressBook()  throws IOException  {

        run();
    }

    private void run() throws IOException {

        loadFromFile();
        displayWelcome();
        takeInput();
        saveToFile();
        displayGoodbye();

    }

    /**
     * Metoden laddar reigstret från binärfil.
     */
    private void loadFromFile() {

        this.register = new Register();

        try {
            FileInputStream fileIn = new FileInputStream("register.data");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            register = (Register) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();

        } catch (ClassNotFoundException c) {

            c.printStackTrace();

        }

    }

    /**
     * Metoden sparar registret till binärfil.
     */
    private void saveToFile(){

        try {
            FileOutputStream fileOut = new FileOutputStream("register.data");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(register);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }

    }

    private void displayGoodbye(){

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
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";


        while (!input.equals(WordChecker.QUIT)) {
            input = br.readLine();
            input = input.trim();

            if (input.length()>WordChecker.ADD.length() &&
                    input.subSequence(0, (WordChecker.ADD.length() +1) ).equals(WordChecker.ADD + " ") ){
                register.add(input);

            } else if (input.equals(WordChecker.LIST)) {
                register.list();
            } else if (input.length()>WordChecker.SEARCH.length() &&
                    input.subSequence(0, (WordChecker.SEARCH.length() +1) ).equals(WordChecker.SEARCH + " ")  ){
                register.search(input);

            }

            else if (input.equals(WordChecker.CLEAR)) {
                register.clear();
            }
            else  if (input.equals(WordChecker.QUIT)){
                break;}


        }
        br.close();

    }

}


