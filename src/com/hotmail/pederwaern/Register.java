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

        contactToAdd = contactToAdd.substring(WordChecker.ADD.length()+1, contactToAdd.length());
        contactToAdd = contactToAdd.trim();

        List<String> wordList = new ArrayList<>();
        Scanner scanner = new Scanner(contactToAdd);

        String firstName="";
        String lastName="";
        String email="";


        //här kontrolleras så att inputen har rätt antal ord(3).

        while (scanner.hasNext()) {
            String word = scanner.next();
            wordList.add(word);
        }
            if(wordList.size() == 3) {
                firstName = wordList.get(0);
                lastName = wordList.get(1);
                email = wordList.get(2);

                //kontroll så att namnen inte innnehåller siffror. Email adressen måste ha formatet av en mailadress.

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

        searchString = searchString.substring(WordChecker.SEARCH.length()+1, searchString.length());
        searchString = searchString.trim();
        searchString = searchString.toLowerCase();
        boolean stringFound= false;

        for (Contact c: register)
        {
            if (c.getFirstName().toLowerCase().startsWith(searchString) || c.getLastName().toLowerCase().startsWith(searchString) ||
                c.getEmail().toLowerCase().startsWith(searchString) ) {
                System.out.println(c.searchResult());
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

