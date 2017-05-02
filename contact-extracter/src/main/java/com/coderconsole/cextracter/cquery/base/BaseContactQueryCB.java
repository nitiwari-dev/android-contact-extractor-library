package com.coderconsole.cextracter.cquery.base;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.coderconsole.cextracter.cmodels.CAccount;
import com.coderconsole.cextracter.cmodels.CEmail;
import com.coderconsole.cextracter.cmodels.CEvents;
import com.coderconsole.cextracter.cmodels.CGroups;
import com.coderconsole.cextracter.cmodels.CName;
import com.coderconsole.cextracter.cmodels.COrganisation;
import com.coderconsole.cextracter.cmodels.CPhone;
import com.coderconsole.cextracter.cmodels.CPostBoxCity;
import com.coderconsole.cextracter.cmodels.ContactGenericType;
import com.coderconsole.cextracter.i.IContactQuery;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Nitesh on 03-04-2017.
 */

public class BaseContactQueryCB implements IContactQuery {

    private static final String TAG = BaseContactQueryCB.class.getSimpleName();

    private Context context;

    private String identity;

    private Cursor cursor;

    private CList cList;

    public BaseContactQueryCB(Context context, Cursor cursor, CList cList, String identity) {
        this.context = context;
        this.identity = identity;
        this.cursor = cursor;
        this.cList = cList;
    }

    ContentResolver getCR() {
        return context.getContentResolver();
    }


    @Override
    public CEmail getEmail() {

        CEmail email = cList.getcEmail();
        if (email == null) {
            email = new CEmail();
        }

        HashSet<String> home = email.getHome();
        HashSet<String> work = email.getWork();
        HashSet<String> mobile = email.getMobile();
        HashSet<String> other = email.getOther();

        int type = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
        String data = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
        String photoUri = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.PHOTO_URI));
        String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DISPLAY_NAME));

        switch (type) {
            case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                work.add(data);
                break;
            case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                home.add(data);
                break;
            case ContactsContract.CommonDataKinds.Email.TYPE_MOBILE:
                mobile.add(data);
                break;
            default:
                other.add(data);
                break;
        }

        if (work.size() == 0 && home.size() == 0 && mobile.size() == 0 && other.size() == 0)
            return null;

        CEmail cEmail = new CEmail();
        cEmail.setWork(work);
        cEmail.setHome(home);
        cEmail.setMobile(mobile);
        cEmail.setOther(other);
        cEmail.setPhotoUri(photoUri);
        return cEmail;
    }

    @Override
    public CPhone getPhone() {

        CPhone cPhone = cList.getcPhone();

        if (cPhone == null) {
            cPhone = new CPhone();
        }
        HashSet<String> homeSet = cPhone.getHome();
        HashSet<String> workSet = cPhone.getWork();
        HashSet<String> mobileSet = cPhone.getMobile();
        HashSet<String> otherSet = cPhone.getMobile();


        String phoneNo = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        String numberType = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
        String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
        String photoUri = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));


        switch (Integer.valueOf(numberType)) {
            case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                homeSet.add(phoneNo);
                break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                workSet.add(phoneNo);
                break;
            case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                mobileSet.add(phoneNo);
                break;
            default:
                otherSet.add(phoneNo);
                break;
        }
        cPhone.setHome(homeSet);
        cPhone.setMobile(mobileSet);
        cPhone.setWork(workSet);
        cPhone.setOther(otherSet);
        cPhone.setDisplayName(displayName);
        cPhone.setPhotoUri(photoUri);

        return cPhone;
    }

    @Override
    public CAccount getAccount() {

        CAccount cAccount = new CAccount();

        List<ContactGenericType> mContactGenericType = new ArrayList<>();

        String accountName = cursor.getString(
                cursor.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_NAME));

        String accountType = cursor.getString(
                cursor.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_TYPE));

        mContactGenericType.add(new ContactGenericType(accountName, accountType));

        cAccount.setmGenericType(mContactGenericType);

        return cAccount;
    }

    @Override
    public CPostBoxCity getPostCode() {

        CPostBoxCity cPostBoxCityList = cList.getcPostCode();

        if (cPostBoxCityList == null) {
            cPostBoxCityList = new CPostBoxCity();
        }


        List<CPostBoxCity.PostCity> cPostCode = cPostBoxCityList.getmPostCity();

        String poBox = cursor.getString(
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));

        String city = cursor.getString(
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));

        String photoUri = cursor.getString(
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.PHOTO_URI));

        String displayName = cursor.getString(
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.DISPLAY_NAME));

        Log.d(TAG, "|Pobox" + poBox + "|City|" + city);
        CPostBoxCity.PostCity postCity = new CPostBoxCity.PostCity();
        postCity.setCity(city);
        postCity.setPost(poBox);

        cPostCode.add(postCity);
        cPostBoxCityList.setPhotoUri(photoUri);
        cPostBoxCityList.setDisplayName(displayName);
        cPostBoxCityList.setmPostCity(cPostCode);

        return cPostBoxCityList;

    }


    @Override
    public CName getName() {

        CName cName = new CName();
        String familyName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
        String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME));
        String givenName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
        String photoUri = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.PHOTO_URI));


        cName.setFamilyName(familyName);
        cName.setDisplayName(displayName);
        cName.setGivenName(givenName);
        cName.setPhotoUri(photoUri);

        return cName;
    }

    @Override
    public COrganisation getOrg() {

        COrganisation cOrganisation = cList.getcOrg();

        if (cOrganisation == null)
            cOrganisation = new COrganisation();

        List<COrganisation.CompanyDepart> companyDeparts = cOrganisation.getCompanyOrgList();

        COrganisation.CompanyDepart depart = new COrganisation.CompanyDepart();
        depart.setCompany(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY)));
        depart.setOrg(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DEPARTMENT)));


        companyDeparts.add(depart);

        cOrganisation.setCompanyOrgList(companyDeparts);
        cOrganisation.setDisplayName(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DISPLAY_NAME)));
        cOrganisation.setPhotoUri(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.PHOTO_URI)));



        return cOrganisation;
    }

    @Override
    public CEvents getEvents() {

        CEvents cEvents = new CEvents();

        String startData = cursor.getString(
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));

        int type = cursor.getInt(
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.TYPE));

        switch (type) {
            case ContactsContract.CommonDataKinds.Event.TYPE_ANNIVERSARY:
                cEvents.setAnniversay(startData);
                break;

            case ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY:
                cEvents.setBirthDay(startData);
                break;
            default:
                break;
        }

        cEvents.setDisplayName(cursor.getString(
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.DISPLAY_NAME)));

        cEvents.setPhotoUri(cursor.getString(
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.PHOTO_URI)));

        return cEvents;
    }

    @Override
    public CGroups getGroups() {

        CGroups cGroups = cList.getcGroups();

        if (cGroups == null)
            cGroups = new CGroups();

        HashSet<CGroups.BaseGroups> baseGroupsList = cGroups.getmList();

        CGroups.BaseGroups baseGroups = new CGroups.BaseGroups();
        baseGroups.setId(cursor.getInt(cursor.getColumnIndex(ContactsContract.Groups._ID)));
        baseGroups.setTitle(cursor.getString(cursor.getColumnIndex(ContactsContract.Groups.TITLE)));
        baseGroupsList.add(baseGroups);

        cGroups.setmList(baseGroupsList);
        return cGroups;

    }

    @Override
    public String getPhotoUri() {
        return cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
    }
}
