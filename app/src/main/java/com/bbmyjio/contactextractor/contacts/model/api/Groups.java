package com.bbmyjio.contactextractor.contacts.model.api;

/**
 * Created by Sandeep.Dude on 21-01-2017.
 */
public class Groups
{
    private String type;

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [type = "+type+"]";
    }
}