package com.hotmail.pederwaern;

/**
 * Denna klass innehåller statiska konstanter och statiska metoder som används av
 * klasserna AdressBook och Register för att enkelt validera namnen och emailadressen i Contact.
 * Metoderna är inte helt tillförlitliga.
 */
public class WordChecker {

    /**
     * Dessa variabler hanterar det kommando som användaren måste ange exakt för att utför
     * metoder. Dessa är case-sensitive ,
     */
    static final String ADD = "add";
    static final String LIST = "list";
    static final String SEARCH = "search";
    static final String QUIT = "quit";
    static final String CLEAR = "clear";


    /**
     *
     * @param name Ett namn.
     * @return true om namnet bestående av endast bokstäver. OBS! Ingen garanti för att strängen är ett riktigt namn.
     */
   static boolean isAWord(String name) {

        for(int i = 0; i<name.length(); i++) {
            if(!Character.isLetter(name.charAt(i)) ) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param emailadress
     * @return true om strängen innehåller minst en punkt och minst ett @. Sista "." måste av högre index i
     * strängen än "@" för att strängen ska kunna vara en giltig mailadress. OBS! ingen garanti för att det är ett
     * giltigt format på emailadressen.
     */
    static boolean isAnEmailAdress(String emailadress) {

        return (emailadress.contains("@") && emailadress.contains(".") &&
                emailadress.lastIndexOf(".") > emailadress.lastIndexOf("@"));


    }
}
