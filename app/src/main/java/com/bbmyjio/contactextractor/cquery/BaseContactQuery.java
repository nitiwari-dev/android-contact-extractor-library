package com.bbmyjio.contactextractor.cquery;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.bbmyjio.contactextractor.cmodels.CAccount;
import com.bbmyjio.contactextractor.cmodels.CEmail;
import com.bbmyjio.contactextractor.cmodels.CEvents;
import com.bbmyjio.contactextractor.cmodels.CGroups;
import com.bbmyjio.contactextractor.cmodels.CName;
import com.bbmyjio.contactextractor.cmodels.COrganisation;
import com.bbmyjio.contactextractor.cmodels.CPostBoxCity;
import com.bbmyjio.contactextractor.cmodels.ContactGenericType;
import com.bbmyjio.contactextractor.contacts.model.api.CPhone;
import com.bbmyjio.contactextractor.i.IContactQuery;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Nitesh on 03-04-2017.
 */

public class BaseContactQuery implements IContactQuery {

    private static final String TAG = BaseContactQuery.class.getSimpleName();

    private Context context;

    private String identity;

    private Cursor cursor;

    private CList cList;

    public BaseContactQuery(Context context, Cursor cursor, CList cList, String identity) {
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
/*        String[] SELECTION_ARGS = new String[]{ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE, identity};

        String SELECTION = ContactsContract.Data.MIMETYPE + " = ? and " + ContactsContract.Data.CONTACT_ID + " = ?";

        String PROJECTIONS[] = new String[]{ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.DATA};

        Cursor emailCursor = getCR().query(ContactsContract.Data.CONTENT_URI, PROJECTIONS,
                SELECTION,
                SELECTION_ARGS,
                ContactsContract.CommonDataKinds.Email.ADDRESS);

        if (emailCursor != null) {

            List<String> home = new ArrayList<>();
            List<String> work = new ArrayList<>();
            List<String> mobile = new ArrayList<>();

            while (emailCursor.moveToNext()) {
                int type = emailCursor.getInt(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                String data = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

                switch (type) {
                    case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                        work.add(data);
                        break;
                    case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                        home.add(data);
                        break;
                    case ContactsContract.CommonDataKinds.Email.TYPE_MOBILE:
                        mobile.add(data);
                    default:
                        break;
                }

            }

            emailCursor.close();

            CEmail email = new CEmail(work, home, mobile);
            return email;

        }

        return null;*/


        CEmail email = cList.getcEmail();
        if (email == null) {
            email = new CEmail();
        }

        HashSet<String> home = email.getHome();
        HashSet<String> work = email.getWork();
        HashSet<String> mobile = email.getMobile();
        HashSet<String> other = email.getMobile();

        int type = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
        String data = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

        switch (type) {
            case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                work.add(data);
                break;
            case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                home.add(data);
                break;
            case ContactsContract.CommonDataKinds.Email.TYPE_MOBILE:
                mobile.add(data);
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
        return cEmail;
    }

    @Override
    public CPhone getPhone() {

       /* String WHERE_CLAUSE = ContactsContract.Data.MIMETYPE + " = ? and " + ContactsContract.Data.CONTACT_ID + " = ?";

        String PROJECTIONS[] = new String[]{ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.NUMBER};

        Cursor pCursor = getCR().query(ContactsContract.Data.CONTENT_URI, PROJECTIONS,
                WHERE_CLAUSE,
                new String[]{ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE, identity}, null);

        if (pCursor != null) {

            HashSet<String> home = new HashSet<>();
            HashSet<String> work = new HashSet<>();
            HashSet<String> mobile = new HashSet<>();

            if (pCursor.getCount() == 0) {
                pCursor.close();
                return null;
            }

            while (pCursor.moveToNext()) {

                int phoneType = pCursor.getInt(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                String phoneNo = pCursor.getString(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


                switch (phoneType) {
                    case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                        home.add(phoneNo);
                        break;
                    case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                        work.add(phoneNo);
                        break;
                    case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                        mobile.add(phoneNo);
                    default:
                        break;
                }
            }

            pCursor.close();

            CPhone cPhone = new CPhone();
            cPhone.setHome(home);
            cPhone.setMobile(mobile);
            cPhone.setWork(work);
            return cPhone;

        }

        return null;*/

        CPhone cPhone = cList.getcPhone();

        if (cPhone == null) {
            cPhone = new CPhone();
        }
        HashSet<String> homeSet = cPhone.getHome();
        HashSet<String> workSet = cPhone.getWork();
        HashSet<String> mobileSet = cPhone.getMobile();

        String phoneNo = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        String numberType = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));

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
    public CAccount getAccount() {

       /* String WHERE_CLAUSE = ContactsContract.RawContacts.A + " =? ";

        String PROJECTIONS[] = new String[]{ContactsContract.RawContacts.ACCOUNT_NAME, ContactsContract.RawContacts.ACCOUNT_TYPE};

        String whereArgs[] = new String[]{identity};
        Cursor cursorPostcode = getCR().query(ContactsContract.RawContacts.CONTENT_URI, PROJECTIONS, WHERE_CLAUSE, whereArgs, null);

        if (cursorPostcode != null) {

            CAccount cAccount = new CAccount();

            List<ContactGenericType> mContactGenericType = new ArrayList<>();

            while (cursorPostcode.moveToNext()) {

                String accountName = cursorPostcode.getString(
                        cursorPostcode.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_NAME));

                String accountType = cursorPostcode.getString(
                        cursorPostcode.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_TYPE));

                String _id = cursorPostcode.getString(
                        cursorPostcode.getColumnIndex(ContactsContract.RawContacts._ID));


                mContactGenericType.add(new ContactGenericType(accountName, accountType));

                //Log.d(TAG, "|Account|" + accountName + "|Account Type|" + accountType + "|_Id|" + _id);
            }

            cursorPostcode.close();

            cAccount.setmGenericType(mContactGenericType);
            return cAccount;
        }

        return null;*/


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
       /* String WHERE_CLAUSE = ContactsContract.Data.MIMETYPE + " = ? and " + ContactsContract.Data.CONTACT_ID + " = ?";

        String WHERE_ARGS[] = new String[]{ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE, identity};

        String PROJECTIONS[] = new String[]{ContactsContract.CommonDataKinds.StructuredPostal.POBOX, ContactsContract.CommonDataKinds.StructuredPostal.CITY};

        Cursor cursorPostcode = getCR().query(ContactsContract.Data.CONTENT_URI, PROJECTIONS, WHERE_CLAUSE, WHERE_ARGS, null);


        if (cursorPostcode != null) {

            List<CPostBoxCity.PostCity> cPostCode = new ArrayList<>();

            if (cursorPostcode.moveToNext()) {
                String poBox = cursorPostcode.getString(
                        cursorPostcode.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));

                String city = cursorPostcode.getString(
                        cursorPostcode.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));


                //Log.d(TAG, "|POSTAL CODE|" + poBox + "|CITY|" + city);

                cPostCode.add(new CPostBoxCity.PostCity(poBox, city));

            }

            CPostBoxCity cPostBoxCity = new CPostBoxCity(cPostCode);

            cursorPostcode.close();

            return cPostBoxCity;

        }

        return null;*/


        CPostBoxCity cPostBoxCityList = cList.getcPostCode();

        if (cPostBoxCityList == null) {
            cPostBoxCityList = new CPostBoxCity();
        }


        List<CPostBoxCity.PostCity> cPostCode = cPostBoxCityList.getmPostCity();

        String poBox = cursor.getString(
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));

        String city = cursor.getString(
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));

        Log.d(TAG, "|Pobox" + poBox + "|City|" + city);
        CPostBoxCity.PostCity postCity = new CPostBoxCity.PostCity();
        postCity.setCity(city);
        postCity.setPost(poBox);

        cPostCode.add(postCity);
        cPostBoxCityList.setmPostCity(cPostCode);

        return cPostBoxCityList;

    }


    @Override
    public CName getName() {
        /*String orgWhere = ContactsContract.Data.MIMETYPE + " = ? and " + ContactsContract.Data.CONTACT_ID + "= ?";
        String[] orgWhereParams = new String[]{
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE, identity};

        String PROJECTIONS[] = new String[]{ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME
                , ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME};

        Cursor orgCur = getCR().query(ContactsContract.Data.CONTENT_URI,
                PROJECTIONS, orgWhere, orgWhereParams, null);

        if (orgCur != null) {
            CName cName = new CName();

            if (orgCur.moveToNext()) {

                String familyName = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
                String displayName = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME));
                String givenName = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));

                cName.setFamilyName(familyName);
                cName.setDisplayName(displayName);
                cName.setGivenName(givenName);

                //Log.d(TAG, "|Family|" + familyName + "displayName" + displayName + "|givent|" + givenName);

            }

            orgCur.close();

            return cName;
        }*/

        CName cName = new CName();
        String familyName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
        String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME));
        String givenName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));

        cName.setFamilyName(familyName);
        cName.setDisplayName(displayName);
        cName.setGivenName(givenName);

        return cName;
    }

    @Override
    public COrganisation getOrg() {
        /*String orgWhere = ContactsContract.Data.MIMETYPE + " = ? and " + ContactsContract.Data.CONTACT_ID + "= ?";
        String[] orgWhereParams = new String[]{
                ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE, identity};

        String PROJECTIONS[] = new String[]{ContactsContract.CommonDataKinds.Organization.COMPANY, ContactsContract.CommonDataKinds.Organization.DEPARTMENT};

        Cursor orgCur = getCR().query(ContactsContract.Data.CONTENT_URI,
                PROJECTIONS, orgWhere, orgWhereParams, null);

        if (orgCur != null) {
            List<COrganisation.CompanyDepart> companyOrgList = new ArrayList<>();
            while (orgCur.moveToNext()) {
                String company = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY));
                String department = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DEPARTMENT));

                companyOrgList.add(new COrganisation.CompanyDepart(company, department));
                //Log.d(TAG, "|COMPANY|" + company + "DEPARTMENT" + department);
            }
            orgCur.close();

            COrganisation cOrganisation = new COrganisation(companyOrgList);

            return cOrganisation;
        }*/

        COrganisation cOrganisation = cList.getcOrg();

        if (cOrganisation == null)
            cOrganisation = new COrganisation();

        List<COrganisation.CompanyDepart> companyDeparts = cOrganisation.getCompanyOrgList();

        COrganisation.CompanyDepart depart = new COrganisation.CompanyDepart();
        depart.setCompany(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY)));
        depart.setOrg(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DEPARTMENT)));


        companyDeparts.add(depart);

        cOrganisation.setCompanyOrgList(companyDeparts);

        return cOrganisation;
    }

    @Override
    public CEvents getEvents() {
        /*String WHERE_CLAUSE = ContactsContract.Data.MIMETYPE + " = ? and " + ContactsContract.Data.CONTACT_ID + " = ?";

        String WHERE_ARGS[] = new String[]{ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE, identity};

        String PROJECTIONS[] = new String[]{ContactsContract.CommonDataKinds.Event.START_DATE, ContactsContract.CommonDataKinds.Event.TYPE};

        Cursor cursorEvents = getCR().query(ContactsContract.Data.CONTENT_URI, PROJECTIONS, WHERE_CLAUSE, WHERE_ARGS, null);

        if (cursorEvents != null) {

            CEvents cEvents = new CEvents();

            while (cursorEvents.moveToNext()) {
                String startData = cursorEvents.getString(
                        cursorEvents.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));

                int type = cursorEvents.getInt(
                        cursorEvents.getColumnIndex(ContactsContract.CommonDataKinds.Event.TYPE));

                switch (type) {
                    case ContactsContract.CommonDataKinds.Event.TYPE_ANNIVERSARY:
                        //Log.d(TAG, "TYPE_ANNIVERSARY" + startData);
                        cEvents.setAnniversay(startData);
                        break;

                    case ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY:
                        cEvents.setBirthDay(startData);
                        break;
                    default:
                        //Log.d(TAG, "TYPE_CUSTOM" + startData);
                        break;
                }

                Log.d(TAG, "|StartDate|" + startData + "|TYPE|" + type);
            }

            cursorEvents.close();
            return cEvents;
        }*/


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


        return cEvents;
    }

    @Override
    public CGroups getGroups() {
     /*   String WHERE = ContactsContract.Data.MIMETYPE + " = ? and " + ContactsContract.Data.CONTACT_ID + "= ?";
        String[] WHERE_ARGS = new String[]{ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE, identity};
        String[] PROJECTIONS = new String[]{ContactsContract.Groups._ID, ContactsContract.CommonDataKinds.GroupMembership.DISPLAY_NAME};

        Cursor groups_cursor = getCR().query(
                ContactsContract.Data.CONTENT_URI,
                PROJECTIONS, WHERE, WHERE_ARGS, null);

        if (groups_cursor != null) {

            List<CGroups.BaseGroups> baseGroupsList = new ArrayList<>();

            while (groups_cursor.moveToNext()) {
                //int id = ;
                //String title =
                CGroups.BaseGroups baseGroups = new CGroups.BaseGroups();
                baseGroups.setId(groups_cursor.getInt(groups_cursor.getColumnIndex(ContactsContract.Groups._ID)));
                baseGroups.setTitle(groups_cursor.getString(groups_cursor.getColumnIndex(ContactsContract.CommonDataKinds.GroupMembership.DISPLAY_NAME)));
                baseGroupsList.add(baseGroups);
            }

            CGroups cGroups = new CGroups();
            cGroups.setmList(baseGroupsList);
            return cGroups;

        }*/

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
       /* ContentResolver contentResolver = getCR();

        try {
            Cursor cursor = contentResolver
                    .query(ContactsContract.Data.CONTENT_URI,
                            null,
                            ContactsContract.Data.CONTACT_ID
                                    + "="
                                    + identity
                                    + " AND "
                                    + ContactsContract.Data.MIMETYPE
                                    + "='"
                                    + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE
                                    + "'", null, null);

            if (cursor != null) {
                if (!cursor.moveToFirst()) {
                    cursor.close();
                    return null; // no photo
                }

                cursor.close();
            } else {
                return null; // error in cursor process
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        Uri person = ContentUris.withAppendedId(
                ContactsContract.Contacts.CONTENT_URI, Long.valueOf(identity));
        return Uri.withAppendedPath(person,
                ContactsContract.Contacts.Photo.CONTENT_DIRECTORY).toString();*/
    }
}
