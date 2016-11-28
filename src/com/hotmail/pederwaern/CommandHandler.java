package com.hotmail.pederwaern;

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
    public void takeInput() throws IOException {
        BufferedReader bufferedR = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
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
        bufferedR.close();

    }

    private boolean isDelete(String input) {

        String[] arguments = input.split(" ");
        return arguments.length > 0 && arguments[0].equals(InputCommand.DELETE);
    }
    private boolean isQuit(String input) {
        return input.equals(InputCommand.QUIT);
    }

    private boolean isSearch(String input) {
        String[] arguments = input.split(" ");
        return arguments.length > 0 && arguments[0].equals(InputCommand.SEARCH);
    }

    private boolean isList(String input) {
        return input.equals(InputCommand.LIST);
    }

    private boolean isAdd(String input) {
        return input.length() > InputCommand.ADD.length()
                && input.subSequence(0, (InputCommand.ADD.length() +1) ).equals(InputCommand.ADD + " ");
    }

    private boolean isHelp(String input) {
        return input.equals(InputCommand.HELP);
    }
}
