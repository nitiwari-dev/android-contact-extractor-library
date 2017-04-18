package com.bbmyjio.contactextractor.cmodels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nitesh on 04-04-2017.
 */

public class CEmail {

    public List<String> work;
    public List<String> home;
    public List<String> mobile;

    public CEmail(List<String> work, List<String> home, List<String> mobile) {
        this.work = new ArrayList<>(work);
        this.home = new ArrayList<>(home);
        this.mobile = new ArrayList<>(mobile);
    }
}
