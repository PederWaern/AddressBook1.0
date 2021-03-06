package com.hotmail.pederwaern.AddressBookApp.logging;

/**
 * Created by pederwaern on 2016-11-24.
 * Loggclass
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;

public class LoggerClass {

    public static void setupLogging() {
        String loggingFilePath = "/Users/pederwaern/IdeaProjects/AdressBokVersion1.0/src/com/hotmail/pederwaern/AddressBookApp/logging/" +
                "logging.properties";
        try (FileInputStream fileInputStream = new FileInputStream(loggingFilePath)) {
            LogManager.getLogManager().readConfiguration(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Could not load log properties.", e);
        }
    }



}
