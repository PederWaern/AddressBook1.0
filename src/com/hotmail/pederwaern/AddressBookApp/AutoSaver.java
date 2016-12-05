package com.hotmail.pederwaern.AddressBookApp;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by pederwaern on 2016-11-28.
 */
public class AutoSaver implements Runnable{

    private FileManager fileManager;
    private Register register;
    boolean keepLooping = true;

    private static final Logger logger = Logger.getLogger(AutoSaver.class.getName());

    public AutoSaver(Register register, FileManager fileManager) {
        this.fileManager = fileManager;
        this.register = register;
    }

    @Override
    public void run() {
        while(keepLooping) {
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "Thread interrupted exception", e);
            }
            logger.info("Autosaving");
            fileManager.saveToFile(register);

        }
    }

    public void stop() {
        keepLooping = false;
    }

}
