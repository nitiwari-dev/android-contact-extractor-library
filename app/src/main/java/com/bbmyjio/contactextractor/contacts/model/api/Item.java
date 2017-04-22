package com.bbmyjio.contactextractor.contacts.model.api;

/**
 * Created by Sandeep.Dude on 21-01-2017.
 */
public class Item
{
    private String name;

    private String type;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

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
        return "ClassPojo [name = "+name+", type = "+type+"]";
    }
}