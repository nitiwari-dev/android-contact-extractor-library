package contacts.model.api;

/**
 * Created by Sandeep.Dude on 21-01-2017.
 */
public class Postal
{
    private String postCode;

    private String city;

    public String getPostCode ()
    {
        return postCode;
    }

    public void setPostCode (String postCode)
    {
        this.postCode = postCode;
    }

    public String getCity ()
    {
        return city;
    }

    public void setCity (String city)
    {
        this.city = city;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [postCode = "+postCode+", city = "+city+"]";
    }
}