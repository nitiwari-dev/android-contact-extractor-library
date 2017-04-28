package com.coderconsole.cextracter.cmodels;

import java.util.HashSet;

/**
 * Created by Nitesh on 04-04-2017.
 */

public class CEmail {

    private HashSet<String> work = new HashSet<>();
    private HashSet<String> home = new HashSet<>();
    private HashSet<String> mobile = new HashSet<>();
    private HashSet<String> other = new HashSet<>();

    public HashSet<String> getOther() {
        return other;
    }

    public void setOther(HashSet<String> other) {
        this.other = other;
    }

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
