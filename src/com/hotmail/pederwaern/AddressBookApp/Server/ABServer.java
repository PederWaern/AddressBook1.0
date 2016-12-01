package com.hotmail.pederwaern.AddressBookApp.Server;

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
        try {
            System.out.println("Server running...");
            serverSocket = new ServerSocket(61616);
            socket = serverSocket.accept();
            InputStreamReader inputStream = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReaderStream = new BufferedReader(inputStream);
            PrintStream printStream = new PrintStream(socket.getOutputStream());

            boolean keepLooping = true;

            while (keepLooping) {

               String clientInput =  bufferedReaderStream.readLine();

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
        String appendedLines="";
        try (Scanner scanner = new Scanner(new FileReader("database.csv"))) {
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