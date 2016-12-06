package com.hotmail.pederwaern.AddressBookApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;


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
                    logger.log(Level.SEVERE, "Thread interrupted", e);
                }
                String input = bufferedR.readLine()
                               .trim();
                if (isAdd(input)) {
                    regHandler.add(input);
                    logger.fine("add typed: " + input);
                } else if (isList(input)) {
                    regHandler.list();
                    logger.fine("list typed: " +  input);
                } else if (isSearch(input)){
                    regHandler.search(input);
                    logger.fine("search typed: " + input);
                } else if (isHelp(input)){
                    logger.fine("help typed: " + input);
                    regHandler.help();
                } else if (isQuit(input)){
                    logger.fine("quit typed: " + input);
                    break;
                } else if (isDelete(input)) {
                    logger.fine("delete typed: " + input);
                    regHandler.delete(input);
                } else {
                    logger.fine("used typed invalid command");
                    System.out.println("Invalid command: " + input);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IO-exception", e);
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
