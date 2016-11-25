package com.hotmail.pederwaern;



/**
 * Created by pederwaern on 2016-11-24.
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.*;

public class LoggerClass implements Serializable {


    public static void setupLogging() {
        String loggingFilePath = "/Users/pederwaern/IdeaProjects/AdressBokVersion1.0/src/com/hotmail/pederwaern/" +
                "logging.properties";
        try (FileInputStream fileInputStream = new FileInputStream(loggingFilePath)) {
            LogManager.getLogManager().readConfiguration(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Could not load log properties.", e);
        }
    }



}
