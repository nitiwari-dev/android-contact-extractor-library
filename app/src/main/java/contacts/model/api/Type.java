package contacts.model.api;

/**
 * Created by Sandeep.Dude on 21-01-2017.
 */
public class Type
{
    private String work;

    private String home;

    public String getWork ()
    {
        return work;
    }

    public void setWork (String work)
    {
        this.work = work;
    }

    public String getHome ()
    {
        return home;
    }

    public void setHome (String home)
    {
        this.home = home;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [work = "+work+", home = "+home+"]";
    }
}
