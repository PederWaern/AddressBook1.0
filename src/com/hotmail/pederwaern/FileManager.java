package com.hotmail.pederwaern;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by pederwaern on 2016-11-28.
 */
public class FileManager {

    private String fileName;

    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    private static final Logger logger = Logger.getLogger(FileManager.class.getName());

    public Register loadFromFile() {

        //try with resources
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            Register reg = (Register) in.readObject();
            in.close();
            return reg;
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "No binary file found to loud", e);
            return null;
        } catch (ClassNotFoundException | IOException e) {
            logger.log(Level.SEVERE, "Class not found or IO exception", e);
            throw new RuntimeException(e);

        }

    }

    public synchronized void saveToFile(Register register){

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(register);
            out.close();
            logger.info("File saved");
        } catch (IOException i) {
            i.printStackTrace();
            logger.log(Level.SEVERE, "IO exception", i);
        }
    }

    public String getFileName() {
        return fileName;
    }



}
