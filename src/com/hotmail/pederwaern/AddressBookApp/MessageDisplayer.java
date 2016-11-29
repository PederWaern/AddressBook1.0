package com.hotmail.pederwaern.AddressBookApp;

import java.util.logging.Logger;

/**
 * Created by pederwaern on 2016-11-28.
 */
public class MessageDisplayer {
    private static final Logger logger = Logger.getLogger(MessageDisplayer.class.getName());

    public void displayGoodbye() {
        logger.info("Program exited by user");
        System.out.println("Exiting program. Goodbye...");
    }

    public void displayWelcome() {
        logger.info("Program started by user");
        System.out.println("Welcome!\nOptions: add\tlist\tsearch\thelp\tdelete\tquit");
    }
}
