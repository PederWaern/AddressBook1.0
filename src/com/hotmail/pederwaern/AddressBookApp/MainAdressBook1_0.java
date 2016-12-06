package com.hotmail.pederwaern.AddressBookApp;

import com.hotmail.pederwaern.AddressBookApp.logging.LoggerClass;

public class MainAdressBook1_0 {

    public static void main(String[] args) {
        LoggerClass.setupLogging();
        new AddressBookApp().start();
    }

}
