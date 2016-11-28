package com.hotmail.pederwaern;

import java.io.IOException;

/**
 * Mainmetod till AddressBook v1.0
 */
public class MainAdressBook1_0 {
//TODO FIXA TILL INPUTSKITEN SÅ DEN INTE LIGGER I ADDRESSBOOKAPP DIREKT
    public static void main(String[] args) throws IOException {
        LoggerClass.setupLogging();
        AddressBookApp addressBook = new AddressBookApp("register.data");
        addressBook.start();
    }

}
