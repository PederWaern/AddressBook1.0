package com.hotmail.pederwaern.AddressBookApp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by pederwaern on 2016-11-28.
 */
public class RegisterHandler {

    private static final Logger logger = Logger.getLogger(RegisterHandler.class.getName());
    private Register localRegister;
    private Register remoteRegister;
    private ArrayList<Contact> localAndRemoteRegs;

     public RegisterHandler(Register locRegister, Register remoteRegister) {


         if (locRegister != null) {
             this.localRegister = locRegister;
         }
         else {
             this.localRegister = new Register(new ArrayList<>());
         }

         if (remoteRegister!= null) {
             this.remoteRegister = remoteRegister;
         }
         else {
             this.remoteRegister = new Register(new ArrayList<>());
         }

         localAndRemoteRegs = new ArrayList<>();
         localAndRemoteRegs.addAll(this.localRegister.getRegister());
         localAndRemoteRegs.addAll(this.remoteRegister.getRegister());

    }

    public Register getRegister() {
        return localRegister;
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

        Contact contact = new Contact(firstName, lastName, email, true);

        localRegister.getRegister().add(contact);
        localAndRemoteRegs.add(contact);

        System.out.println("Contact has been added");
        logger.info("Contact added to Adressbook by user");
    }


    /**
     * Listar alla kontakter i adressboken.
     */
    public void list() {
        if (localAndRemoteRegs.size()==0){
            System.out.println("Adressbook is empty.");
            return;
        }

        //skapar en kopia av registret och sorterar kontakterna på förnamn.
        List<Contact> sortedList = new ArrayList<>();
        sortedList.addAll(localAndRemoteRegs);
        Collections.sort(localAndRemoteRegs, new FirstNameComparator());

        for (Contact contact: localAndRemoteRegs
                ) {
            System.out.println(listResultFormat(contact));

        }
        logger.info("User lists contacts in Adressbook");
    }

    /**
     * Sökfunktionen tillämpar "Starts with".
     * @param searchString
     */
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

        // söker efter strängen i registret och avbryter loopen om id har hittats.
        for (int i = 0; i < localRegister.getRegister().size(); i++) {
            if (localRegister.getRegister().get(i).getId().equals(arguments[1])) {
                localRegister.getRegister().remove(i);
                contactFound = true;
                //bryt loop
                i = localRegister.getRegister().size();
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

}
