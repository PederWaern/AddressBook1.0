package com.hotmail.pederwaern.AddressBookApp;

import java.util.Comparator;

public class FirstNameComparator implements Comparator<Contact> {

    @Override
    public int compare(Contact o1, Contact o2) {
        return o1.getFirstName().toLowerCase().compareTo(o2.getFirstName().toLowerCase());
    }
}
