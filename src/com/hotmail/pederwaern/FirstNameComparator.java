package com.hotmail.pederwaern;

import java.util.Comparator;

/**
 * Created by pederwaern on 2016-11-24.
 */
public class FirstNameComparator implements Comparator<Contact> {

    @Override
    public int compare(Contact o1, Contact o2) {
        return o1.getFirstName().toLowerCase().compareTo(o2.getFirstName().toLowerCase());
    }
}
