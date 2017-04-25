package com.bbmyjio.contactextractor.contacts.model.api;

import java.util.HashSet;

/**
 * Create Phone of ContactList
 *
 * Created by nitesh tiwari on 21-01-2017.
 */
public class CPhone {
    private HashSet<String> work = new HashSet<>();
    private HashSet<String> home = new HashSet<>();
    private HashSet<String> mobile = new HashSet<>();

    public HashSet<String> getWork() {
        return work;
    }

    public void setWork(HashSet<String> work) {
        this.work = work;
    }

    public HashSet<String> getHome() {
        return home;
    }

    public void setHome(HashSet<String> home) {
        this.home = home;
    }

    public HashSet<String> getMobile() {
        return mobile;
    }

    public void setMobile(HashSet<String> mobile) {
        this.mobile = mobile;
    }
}