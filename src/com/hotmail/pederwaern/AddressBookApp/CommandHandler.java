package com.hotmail.pederwaern.AddressBookApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

/**
 * Created by pederwaern on 2016-11-28.
 */
public class CommandHandler {

    private static final Logger logger = Logger.getLogger(CommandHandler.class.getName());
    private RegisterHandler regHandler;

    public CommandHandler(RegisterHandler regHandler) {
        this.regHandler = regHandler;

    }
    public void takeInput() {
        try (BufferedReader bufferedR = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String input = bufferedR.readLine().trim();
                if (isAdd(input)) {
                    regHandler.add(input);
                    logger.fine("add typed");
                } else if (isList(input)) {
                    regHandler.list();
                    logger.fine("log typed");
                } else if (isSearch(input)){
                    regHandler.search(input);
                    logger.fine("search typed");
                } else if (isHelp(input)){
                    logger.fine("help typed");
                    regHandler.help();
                } else if (isQuit(input)){
                    logger.fine("quit typed");
                    break;
                } else if (isDelete(input)) {
                    logger.fine("delete typed");
                    regHandler.delete(input);
                } else {
                    logger.fine("used typed invalid command");
                    System.out.println("Invalid command");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isDelete(String input) {

        String[] arguments = input.split(" ");
        return arguments.length > 0 && arguments[0].equals(InputCommand.DELETE);
    }
    private boolean isQuit(String input) {
        String[] arguments = input.split(" ");
        if (arguments.length > 1 && arguments[0].equals(InputCommand.QUIT)){
            System.out.println("Invalid amount of parameters, no parameters allowed");
        } else if (arguments.length == 1 && arguments[0].equals(InputCommand.QUIT)) {
            return true;
        }
        return false;
    }

    private boolean isSearch(String input) {
        String[] arguments = input.split(" ");
        return arguments.length > 0 && arguments[0].equals(InputCommand.SEARCH);
    }

    private boolean isList(String input) {
        String[] arguments = input.split(" ");
        if (arguments.length > 1 && arguments[0].equals(InputCommand.LIST)){
            System.out.println("Invalid amount of parameters, no parameters allowed");
        } else if (arguments.length == 1 && arguments[0].equals(InputCommand.LIST)) {
            return true;
        }
        return false;
    }

    private boolean isAdd(String input) {
        String[] arguments = input.split(" ");
        return arguments.length > 0 && arguments[0].equals(InputCommand.ADD);

    }

    private boolean isHelp(String input) {
        return input.equals(InputCommand.HELP);
    }
}
