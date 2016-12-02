package com.hotmail.pederwaern.AddressBookApp;

import com.hotmail.pederwaern.AddressBookApp.logging.LoggerClass;

/**
 * Mainmetod till AddressBook v1.0
 */
public class MainAdressBook1_0 {
//TODO fixa felhantering om clienten inte hittar servern...


    public static void main(String[] args) {
        LoggerClass.setupLogging();
        AddressBookApp addressBook = new AddressBookApp("register.data");
        addressBook.start();
    }

}
