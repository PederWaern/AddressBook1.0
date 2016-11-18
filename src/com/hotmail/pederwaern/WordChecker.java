package com.hotmail.pederwaern;

/**
 * Denna klass innehåller statiska metoder som används av
 * klasserna AdressBook och Register för att enkelt validera namnen och emailadressen i Contact.
 * OBS! Metoderna är inte helt tillförlitliga att validera!
 */
public class WordChecker {

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

        return ((emailadress.contains("@") && emailadress.contains(".")) &&
                emailadress.lastIndexOf(".") > emailadress.lastIndexOf("@"));


    }
}
