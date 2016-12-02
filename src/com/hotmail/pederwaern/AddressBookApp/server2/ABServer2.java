package com.hotmail.pederwaern.AddressBookApp.server2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ABServer2 {

    ServerSocket serverSocket;
    Socket socket;


    public static void main(String[] args) {
        ABServer2 server = new ABServer2();
        server.go();

    }

    private void go() {
        System.out.println("server 2 running...");
        try {
            serverSocket = new ServerSocket(61617);
            socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try (InputStreamReader inputStream = new InputStreamReader(socket.getInputStream());
             BufferedReader bufferedReaderStream = new BufferedReader(inputStream);
             PrintStream printStream = new PrintStream(socket.getOutputStream())
        ) {

            boolean keepLooping = true;
            while (keepLooping) {

                String clientInput = bufferedReaderStream.readLine();

                switch (clientInput) {
                    case "getall":
                        printStream.println(parseCSVfile());
                        printStream.flush();
                        keepLooping = false;
                        break;
                    case "exit":
                        keepLooping = false;
                        break;
                    default:
                        break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private String parseCSVfile() {
        String line;
        String appendedLines = "";
        try (Scanner scanner = new Scanner(new FileReader("databasetwo.csv"))) {
            while (scanner.hasNextLine()) {
                line = scanner.nextLine()
                        .concat("\n");
                appendedLines += line;
            }

            return appendedLines;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

}