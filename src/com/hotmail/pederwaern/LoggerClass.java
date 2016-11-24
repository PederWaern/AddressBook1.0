package com.hotmail.pederwaern;



/**
 * Created by pederwaern on 2016-11-24.
 */

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.*;

public class LoggerClass implements Serializable {

    public static final Logger LOGGER = Logger.getLogger(LoggerClass.class.getName());
    private static FileHandler fh;

    public void init(){
        try {
            fh = new FileHandler("log.log", true);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
        Logger l = Logger.getLogger("");

        //denna rad gör så att det inte skrivs ut i konsolen?
        LogManager.getLogManager().reset();
        fh.setFormatter(new SimpleFormatter());
        l.addHandler(fh);
        l.setLevel(Level.FINE);
    }

    public void infoMessageToLog(String message) {
        LOGGER.info(message);
    }

    public void fineMessageToLog(String message) {
        LOGGER.fine(message);
    }

    public void severeMessageToLog(String message) {
        LOGGER.severe(message);
    }

}
