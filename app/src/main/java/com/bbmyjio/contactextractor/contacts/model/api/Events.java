package com.bbmyjio.contactextractor.contacts.model.api;

/**
 * Created by Sandeep.Dude on 21-01-2017.
 */
public class Events
{
    private String anniversary;

    private String birthDay;

    public String getAnniversary ()
    {
        return anniversary;
    }

    public void setAnniversary (String anniversary)
    {
        this.anniversary = anniversary;
    }

    public String getBirthDay ()
    {
        return birthDay;
    }

    public void setBirthDay (String birthDay)
    {
        this.birthDay = birthDay;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [anniversary = "+anniversary+", birthDay = "+birthDay+"]";
    }
}
