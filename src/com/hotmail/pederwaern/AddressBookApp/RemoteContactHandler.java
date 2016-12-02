package com.hotmail.pederwaern.AddressBookApp;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class RemoteContactHandler implements Runnable {

    private RegisterHandler registerHandler;
    private boolean keepLooping;

    public RemoteContactHandler() {


        registerHandler = new RegisterHandler();
        keepLooping = true;
        remoteC = new ArrayList<>();
        run();
    }


    private ArrayList<Contact> remoteC;
    private String contactLine;

    @Override
    public void run() {

        while (keepLooping) {
            getContacts();
            parseContacts();
            copyContactsToRegisterHandler();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stop();

        }
    }

    public void stop() {
        keepLooping = false;
    }
    private void copyContactsToRegisterHandler() {
        registerHandler.setRemoteRegister(remoteC);
    }
    private void parseContacts() {

        String ID;
        String firstName;
        String lastName;
        String email;
        String line="";
        String[] arguments;

        try (Scanner scanner = new Scanner(contactLine)) {
            while (scanner.hasNext()) {
                line = scanner.nextLine();
                arguments = line.split(",");
                ID = arguments[0];
                firstName = arguments[1];
                lastName = arguments[2];
                email = arguments[3];

                remoteC.add((new Contact(ID, firstName, lastName, email, false)));
            }

        } catch (NullPointerException e) {
            //TODO logga felet
        }

    }

    private void getContacts() {

        try {
            Socket socket = new Socket(Connection.HOST, Connection.PORT);
            InputStreamReader inputStream = new InputStreamReader(socket.getInputStream());


            Scanner inputScanner = new Scanner(inputStream);
            PrintStream printStream = new PrintStream(socket.getOutputStream());

            String total = "";

            printStream.println("getall");

            while (inputScanner.hasNextLine()){
                String line = inputScanner.nextLine();
                total = total + line + "\n";
            }
            printStream.flush();
            printStream.println("exit");
            printStream.flush();
            printStream.close();
            socket.close();

            contactLine = total;

        } catch (IOException e) {
            System.out.println("WARNING: Could not load remote register correctly\n");
          //  e.printStackTrace();
        }
    }



}

