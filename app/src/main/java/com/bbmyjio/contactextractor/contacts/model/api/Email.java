package com.bbmyjio.contactextractor.contacts.model.api;

/**
 * Created by Sandeep.Dude on 21-01-2017.
 */
public class Email
{
    private Type type;

    public Type getType ()
    {
        return type;
    }

    public void setType (Type type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [type = "+type+"]";
    }
}

