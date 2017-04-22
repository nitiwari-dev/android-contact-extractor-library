package com.bbmyjio.contactextractor.contacts.model.api;

/**
 * Created by Sandeep.Dude on 21-01-2017.
 */
public class Name
{
    private String familyName;

    private String givenName;

    private String displayName;

    public String getFamilyName ()
    {
        return familyName;
    }

    public void setFamilyName (String familyName)
    {
        this.familyName = familyName;
    }

    public String getGivenName ()
    {
        return givenName;
    }

    public void setGivenName (String givenName)
    {
        this.givenName = givenName;
    }

    public String getDisplayName ()
    {
        return displayName;
    }

    public void setDisplayName (String displayName)
    {
        this.displayName = displayName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [familyName = "+familyName+", givenName = "+givenName+", displayName = "+displayName+"]";
    }
}