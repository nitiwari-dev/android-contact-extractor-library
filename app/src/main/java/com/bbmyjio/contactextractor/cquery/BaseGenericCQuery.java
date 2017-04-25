package com.bbmyjio.contactextractor.cquery;

import android.database.Cursor;
import android.provider.ContactsContract;

import com.bbmyjio.contactextractor.contacts.model.api.CPhone;

import java.util.HashSet;

/**
 * Created by Nitesh on 24-04-2017.
 */

public class BaseGenericCQuery implements IGenericQuery {

    private Cursor fetchCursor;

    private String _id;

    private String contactId;

    private GenericCList genericContact;


    public BaseGenericCQuery(Cursor fetchCursor, GenericCList cListMap, String _id, String contactId) {
        this.fetchCursor = fetchCursor;
        this._id = _id;
        this.contactId = contactId;
        this.genericContact = cListMap;
    }

    @Override
    public CPhone getPhone() {
        CPhone cPhone = genericContact.getcPhone();

        if (cPhone == null){
            cPhone = new CPhone();
        }
        HashSet<String> homeSet = cPhone.getHome();
        HashSet<String> workSet = cPhone.getWork();
        HashSet<String> mobileSet = cPhone.getMobile();

        String phoneNo = fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        String numberType = fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));

        switch (Integer.valueOf(numberType)) {
            case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                homeSet.add(phoneNo);
                break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                workSet.add(phoneNo);
                break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                mobileSet.add(phoneNo);
            default:
                break;
        }
        cPhone.setHome(homeSet);
        cPhone.setMobile(mobileSet);
        cPhone.setWork(workSet);
        return cPhone;
    }

    @Override
    public String getName() {
        String displayName = fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));                        //Name of contact
        return displayName;
    }

    @Override
    public String getPhotoUri() {
        String uri = fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
        return uri;
    }
}
