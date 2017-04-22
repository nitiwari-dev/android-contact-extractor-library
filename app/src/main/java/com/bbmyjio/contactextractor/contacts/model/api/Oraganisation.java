package com.bbmyjio.contactextractor.contacts.model.api;

/**
 * Created by Sandeep.Dude on 21-01-2017.
 */
public class Oraganisation
{
    private String department;

    private String company;

    public String getDepartment ()
    {
        return department;
    }

    public void setDepartment (String department)
    {
        this.department = department;
    }

    public String getCompany ()
    {
        return company;
    }

    public void setCompany (String company)
    {
        this.company = company;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [department = "+department+", company = "+company+"]";
    }
}