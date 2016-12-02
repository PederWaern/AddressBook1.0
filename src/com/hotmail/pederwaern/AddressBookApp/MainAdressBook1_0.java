package com.hotmail.pederwaern.AddressBookApp;

import com.hotmail.pederwaern.AddressBookApp.logging.LoggerClass;

/**
 * Mainmetod till AddressBook v1.0
 */
public class MainAdressBook1_0 {
    public static void main(String[] args) {
        //TODO refaktorera allmänt...invänta feedback och snygga upp registerhandler klassen...ev servern..
        LoggerClass.setupLogging();
        AddressBookApp addressBook = new AddressBookApp("register.data");
        addressBook.start();
    }

}
