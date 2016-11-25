package com.hotmail.pederwaern;

import java.io.IOException;

/**
 * Mainmetod till AddressBook v1.0
 */
public class MainAdressBook1_0 {

    public static void main(String[] args) throws IOException {
        LoggerClass.setupLogging();
        AddressBook addressBook = new AddressBook("register.data");
        addressBook.start();

    }

}
