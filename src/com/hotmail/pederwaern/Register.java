package com.hotmail.pederwaern;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Denna klass hanterar själva listan som kontakterna hamnar i, samt hanterar de metoder som användare gör
 * på listan.
 *
 * Created by pederwaern on 2016-11-16.
 */
public class Register implements Serializable  {

    public List<Contact> register;
    private LoggerClass log;

    public Register(List<Contact> register) {
        this.register = register;
        log = new LoggerClass();

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

        // kontroll så att namnen inte innnehåller siffror. Email adressen måste ha innehålla '.' och '@'.

        if (WordChecker.isWord(firstName)
                && WordChecker.isWord(lastName)
                && WordChecker.isEmailAddress(email)) {
            register.add(new Contact(firstName, lastName, email));
            System.out.println("Contact has been added");
        } else {
            System.out.println("ERROR - invalid format");
        }
    }

    /**
     * Listar alla kontakter i adressboken.
     */
    public void list() {
        if (register.size()==0){
            System.out.println("Adressbook is empty.");
            return;
        }

        //sorterar listan
        Collections.sort(register, new FirstNameComparator());

        for (Contact c: register
             ) {
            System.out.println(c);
        }

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

        //sorterar listan
        Collections.sort(register, new FirstNameComparator());

        for (Contact contact: register)
        {
            if        (contact.getFirstName().toLowerCase().startsWith(searchString)
                    || contact.getLastName().toLowerCase().startsWith(searchString)
                    || contact.getEmail().toLowerCase().startsWith(searchString) ) {
                System.out.println(contact.searchResultToString());
                stringFound = true;
            }

        }

        if (!stringFound) {
            System.out.println("Entry not found");
        }

    }

    //OBS! Ej officielt implementerad i UI ännu.

    /*public void clear () {
        register.clear();
    }*/

    public void help() {
        System.out.println("add\t\t-- Adds a new contact");
        System.out.println("delete\t-- Deletes a contact");
        System.out.println("list\t-- Lists all contacts");
        System.out.println("help\t -- Show help");
    }

    public void delete(String deleteString) {
        String trimmedDeleteString = deleteString.trim();
        String[] arguments = deleteString.split(" ");

        if (arguments.length != 2) {
            System.out.println("Invalid amount of parameters");
            return;
        }

        boolean contactFound = false;
        int contactIndex=0;


        // TODO - Gör om till vanlig for loop för att slippa gå igenom hela listan om UUID redan är hittat

        for (Contact contact: register) {
            if (contact.getId().equals(arguments[1]) ){
                contactFound = true;
                contactIndex = register.indexOf(contact);
            }
        }
        if (contactFound) {
            System.out.println("Entry" + register.get(contactIndex).getId() + "has been deleted.");
            register.remove(contactIndex);
        } else {
            System.out.println("Entry not found");
        }


    }
}

