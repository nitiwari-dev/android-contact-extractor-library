package com.coderconsole.cextracter.cmodels;

/**
 * Contact Name Information
 *
 * Created by Nitesh on 18-04-2017.
 */

public class CName {

    private String familyName;
    private String displayName;
    private String givenName;

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }
}
