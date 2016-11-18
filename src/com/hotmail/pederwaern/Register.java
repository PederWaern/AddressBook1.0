package com.hotmail.pederwaern;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Denna klass hanterar själva listan som kontakterna hamnar i, samt hanterar de metoder som användare gör
 * på listan.
 *
 * Created by pederwaern on 2016-11-16.
 */
public class Register implements Serializable{

    public ArrayList<Contact> register;

    public Register() {
        this.register = new ArrayList<>();

    }

    public void add(String contactToAdd) {

        //kommandot "add " tas bort från strängen och blanka tecken före och efter.
        contactToAdd = contactToAdd.substring(InputCommand.ADD.length()+1, contactToAdd.length());
        contactToAdd = contactToAdd.trim();

        List<String> tempWordList = new ArrayList<>();
        Scanner scanner = new Scanner(contactToAdd);

        String firstName="";
        String lastName="";
        String email="";

        //här kontrolleras så att inputen har rätt antal ord(3).

        while (scanner.hasNext()) {
            String word = scanner.next();
            tempWordList.add(word);
        }
            if(tempWordList.size() == 3) {
                firstName = tempWordList.get(0);
                lastName = tempWordList.get(1);
                email = tempWordList.get(2);

                //kontroll så att namnen inte innnehåller siffror. Email adressen måste ha innehålla '.' och '@'.

                if(WordChecker.isAWord(firstName) && WordChecker.isAWord(lastName) && WordChecker.isAnEmailAdress(email)) {
                    register.add(new Contact(firstName, lastName, email));
                }
                else {
                    System.out.println("ERROR - invalid format");
                }
            }
            else {
                System.out.println("ERROR- invalid amount of parameters");

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

        for (Contact c: register
             ) {
            System.out.println(c);
        }


    }

    /**
     * Sökfunktionen tillämpar
     * @param searchString
     */
    public void search (String searchString){

        searchString = searchString.substring(InputCommand.SEARCH.length()+1, searchString.length());
        searchString = searchString.trim();
        searchString = searchString.toLowerCase();
        boolean stringFound = false;

        for (Contact contact: register)
        {
            if (contact.getFirstName().toLowerCase().startsWith(searchString) ||
                contact.getLastName().toLowerCase().startsWith(searchString) ||
                contact.getEmail().toLowerCase().startsWith(searchString) ) {
                System.out.println(contact.searchResultToString());
                stringFound = true;
            }

        }

        if(!stringFound) {
            System.out.println("Entry not found");
        }

    }

    //OBS! Ej officielt implementerad i UI ännu.

    public void clear () {
        register.clear();
    }
}

