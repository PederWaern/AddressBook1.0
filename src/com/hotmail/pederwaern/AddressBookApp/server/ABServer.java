package com.hotmail.pederwaern.AddressBookApp.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ABServer {

    ServerSocket serverSocket;
    Socket socket;


    public static void main(String[] args) {
        ABServer server = new ABServer();
        server.go();

    }

    private void go() {
        System.out.println("server 1 running...");
        try {
            serverSocket = new ServerSocket(61616);
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
                      //  System.out.println("getall from client");
                        break;
                    case "exit":
                       // System.out.println("exit from client");
                        keepLooping = false;
                        break;
                    default:
                        break;
                }
            }
            bufferedReaderStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String parseCSVfile() {
        String line;
        String appendedLines = "";
        try (Scanner scanner = new Scanner(new FileReader("database.csv"))) {
            while (scanner.hasNextLine()) {
                line = scanner.nextLine() + "\n";
                appendedLines += line;
            }
            return appendedLines.trim()
                    .replace(",", " ");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return appendedLines;

    }

}