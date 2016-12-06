package com.hotmail.pederwaern.AddressBookApp;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RegisterHandler {

    private static final Logger logger = Logger.getLogger(RegisterHandler.class.getName());

    private ArrayList<Contact> remoteRegister;
    private Register localRegister;

    public RegisterHandler(Register locRegister) {
        if (locRegister != null) {
            this.localRegister = locRegister;
        }
        else {
            this.localRegister = new Register(new ArrayList<>());
        }
        remoteRegister = new ArrayList<>();
    }

    public Register getLocalRegister() {
        return localRegister;
    }


    public void loadRemoteRegister(String serverName, int port) {
        Thread remote = new Thread(new RemoteHandler(serverName, port));
        remote.start();
    }

    public void add(String inputString) {
        //kommandot "add " tas bort från strängen och blanka tecken före och efter.
        String[] words = inputString.split(" ");
        if (words.length != 4) {
            System.out.println("ERROR- invalid amount of parameters");
            return;
        }

        String firstName = words[1];
        String lastName = words[2];
        String email = words[3];

        Contact contact = new Contact(firstName, lastName, email);

        localRegister.getContactList().add(contact);

        System.out.println("Contact has been added");
        logger.info("Contact added to Adressbook by user");
    }

    /**
     * Listar alla kontakter i adressboken.
     */
    public void list() {
        if (localRegister.getContactList().size()== 0
                && remoteRegister.size()== 0){
            System.out.println("Adressbook is empty.");
            return;
        }

        //skapar en kopia av registret och sorterar kontakterna på förnamn.
        List<Contact> sortedList = new ArrayList<>();
        sortedList.addAll(localRegister.getContactList());
        sortedList.addAll(remoteRegister);

        Collections.sort(sortedList, new FirstNameComparator());

        for (Contact contact: sortedList
                ) {
            System.out.println(listResultFormat(contact));

        }
        logger.info("User lists contacts in Adressbook");
    }


    public void search (String searchString){

        String[] arguments = searchString.split(" ");

        //kollar att antalet ord är rätt
        if (arguments.length != 2) {
            System.out.println("Invalid amount of parameters");
            return;
        }
        searchString = searchString.substring(InputCommand.SEARCH.length()+1, searchString.length())
                .trim()
                .toLowerCase();

        boolean stringFound = false;
        List<Contact> listOfFoundEntries = new ArrayList<>();

        logger.info("User searched the register");

        ArrayList<Contact> localAndRemoteRegs = new ArrayList<>();
        localAndRemoteRegs.addAll(localRegister.getContactList());
        localAndRemoteRegs.addAll(remoteRegister);

        for (Contact contact: localAndRemoteRegs) {
            if        (contact.getFirstName().toLowerCase().startsWith(searchString)
                    || contact.getLastName().toLowerCase().startsWith(searchString)
                    || contact.getEmail().toLowerCase().startsWith(searchString) ) {

                listOfFoundEntries.add(contact);
                stringFound = true;
            }

        }
        //om söksträngen hittats skriv ut de kontakter som matchar i bokstavsordning
        if (stringFound) {
            Collections.sort(listOfFoundEntries, new FirstNameComparator());
            for (Contact contact: listOfFoundEntries) {
                System.out.println(searchResultFormat(contact));
            }

        } else {
            System.out.println("Entry not found");
        }

    }

    public void help() {
        System.out.println("add\t\t-- Adds a new contact");
        System.out.println("delete\t-- Deletes a contact");
        System.out.println("list\t-- Lists all contacts");
        System.out.println("help\t -- Show help");
        logger.info("User displayed help menu");
    }

    public void delete(String deleteString) {
        String trimmedDeleteString = deleteString.trim();
        String[] arguments = trimmedDeleteString.split(" ");


        if (arguments.length != 2) {
            System.out.println("Invalid amount of parameters");
            return;
        }
        String idToDelete = arguments[1];
        boolean contactFound = false;

        //kollar om kontakten är del av det remota registret, isåfall returnerar metoden med ett msg till användaren.
        for (Contact remoteContact : remoteRegister) {
            if (remoteContact.getId().equals(idToDelete)) {
                System.out.println("Cannot delete remote contact");
                return;
            }
        }

        // söker efter strängen i registret och avbryter loopen om id har hittats.
        for (int i = 0; i < localRegister.getContactList().size(); i++) {
            if (localRegister.getContactList().get(i).getId().equals(idToDelete)) {
                localRegister.getContactList().remove(i);
                contactFound = true;
                //bryt loop
                i = localRegister.getContactList().size();
            }

        }
        if (contactFound) {
            System.out.println("Entry: " + idToDelete + " has been deleted.");
            logger.info("User deleted the entry: " + idToDelete);

        } else {
            System.out.println("Entry not found");
        }
    }

    private String searchResultFormat (Contact contact) {
        return contact.getId() + " " + contact.getFirstName() + " " + contact.getLastName() + " " + contact.getEmail();
    }

    private String listResultFormat (Contact contact){
        return "Id:" + contact.getId() + "\n" +
                "Firstname: " + contact.getFirstName() + "\n" +
                "Lastname: " + contact.getLastName() + "\n" +
                "Mailadress:" + contact.getEmail() + "\n";
    }


    //-------privat klass----------------------------------------------------------------------------------


    private class RemoteHandler implements Runnable {

        private boolean keepLooping;
        final int port;
        final String serverName;
        private ArrayList<Contact> remoteContacts;
        private String contactLine;

        public RemoteHandler(String serverName, int port) {
            this.port = port;
            this.serverName = serverName;
            keepLooping = true;
            remoteContacts = new ArrayList<>();
            run();
        }

        @Override
        public synchronized void run() {
            while (keepLooping) {
                getContacts();
                parseContacts();
                copyContactsToRegisterHandler();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    logger.log(Level.SEVERE, "Thread interrupted exception",e);
                }
                stop();

            }
        }

        public void stop() {
            keepLooping = false;
        }
        private void copyContactsToRegisterHandler() {
            remoteRegister.addAll(remoteContacts);
        }
        private void parseContacts() {

            String ID;
            String firstName;
            String lastName;
            String email;
            String[] arguments;

            try (Scanner scanner = new Scanner(contactLine)) {
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    arguments = line.split(" ");
                    ID = arguments[0];
                    firstName = arguments[1];
                    lastName = arguments[2];
                    email = arguments[3];

                    remoteContacts.add((new Contact(ID, firstName, lastName, email)));
                    logger.info("Contact ID " + ID + " from " + serverName + " on port " + port + " was imported successfully");

                }

            } catch (NullPointerException e) {
                logger.log(Level.SEVERE, "Nullpointer exception",e);
            }

        }

        private void getContacts() {
            try(
                    Socket socket = new Socket(ConnectionInfo.HOST, port);
                    InputStreamReader inputStream = new InputStreamReader(socket.getInputStream());
                    PrintStream printStream = new PrintStream(socket.getOutputStream());
                    Scanner inputScanner = new Scanner(inputStream)
            ) {
                String total = "";

                printStream.println("getall\nexit");
                printStream.flush();
                while (inputScanner.hasNextLine()){
                    String line = inputScanner.nextLine();
                    total = total + line + "\n";
                }
                this.contactLine = total;

            } catch (IOException e) {
                System.out.println("WARNING: Remote contacts from " + serverName + " may not have been downloaded correctly\n" );
                logger.log(Level.SEVERE, "Exception in getting remote register", e);
            }

        }
    }

}