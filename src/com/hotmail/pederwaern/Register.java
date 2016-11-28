package com.hotmail.pederwaern;

import java.io.Serializable;
import java.util.List;

/**
 * Denna klass hanterar själva listan som kontakterna hamnar i, samt hanterar de metoder som användare gör
 * på listan.
 *
 * Created by pederwaern on 2016-11-16.
 */
public class Register implements Serializable  {

    private List<Contact> register;

    public Register(List<Contact> register) {
        this.register = register;
    }

    public List<Contact> getRegister() {
        return register;
    }


}

