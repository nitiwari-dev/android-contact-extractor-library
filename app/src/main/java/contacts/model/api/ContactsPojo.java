package contacts.model.api;

/**
 * Created by Sandeep.Dude on 21-01-2017.
 */
public class ContactsPojo
{
    private ContactsInfo[] contactsInfo;

    public ContactsInfo[] getContactsInfo ()
    {
        return contactsInfo;
    }

    public void setContactsInfo (ContactsInfo[] contactsInfo)
    {
        this.contactsInfo = contactsInfo;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [contactsInfo = "+contactsInfo+"]";
    }
}