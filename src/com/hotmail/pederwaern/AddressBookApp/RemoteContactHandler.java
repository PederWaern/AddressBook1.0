package com.hotmail.pederwaern.AddressBookApp;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class RemoteContactHandler implements Runnable {

    public RemoteContactHandler() {
        remoteC = new ArrayList<>();
        run();
    }

    public ArrayList<Contact> getRemoteContacts() {
        return remoteC;
    }

    private ArrayList<Contact> remoteC;
    private String contactLine;




    public void run() {

        getContacts();
        parseContacts();
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
                Thread.sleep(50);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void getContacts() {

        try {
            Socket socket = new Socket("localhost", 61616);
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

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
