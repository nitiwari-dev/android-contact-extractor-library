package com.bbmyjio.contactextractor.contacts.model.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Create Phone of ContactList
 *
 * Created by nitesh tiwari on 21-01-2017.
 */
public class CPhone {
    public List<String> work;

    public List<String> home;

    public List<String> mobile;

    public CPhone(List<String> work, List<String> home, List<String> mobile) {
        this.work = new ArrayList<>(work);
        this.home = new ArrayList<>(home);
        this.mobile = new ArrayList<>(mobile);
    }
}