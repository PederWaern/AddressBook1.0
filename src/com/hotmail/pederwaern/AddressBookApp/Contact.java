package com.hotmail.pederwaern.AddressBookApp;

import java.io.Serializable;
import java.util.UUID;

/**
 * Contact är en kontakt i en adressbok som innehåller ID(UUID), förnamn, efternamn och emailadress.
 *
 */
public class Contact implements Serializable{


    private String id;
    private String firstName;
    private String lastName;
    private String email;

    public boolean isLocal() {
        return isLocal;
    }

    private boolean isLocal;


    public Contact(String firstName, String lastName, String email, boolean isLocal) {

        createId();
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        this.isLocal = isLocal;


    }

    public Contact(String UUID, String firstName, String lastName, String email, boolean isLocal) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        this.id = UUID;
        this.isLocal = isLocal;

    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    private void createId() {

        UUID id = UUID.randomUUID();
        String stringID = id.toString();
        stringID = stringID.substring(0,13);
        setId(stringID);


    }

    @Override
    public String toString() {
        return "" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    private void setId(String id) {
        this.id = id;
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private void setEmail(String email) {
        this.email = email;
    }


}