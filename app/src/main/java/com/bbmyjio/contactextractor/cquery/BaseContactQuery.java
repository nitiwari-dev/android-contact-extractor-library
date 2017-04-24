package com.bbmyjio.contactextractor.cquery;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
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
import java.util.List;

/**
 * Created by Nitesh on 03-04-2017.
 */

public class BaseContactQuery implements IContactQuery {

    private static final String TAG = BaseContactQuery.class.getSimpleName();

    private Context context;

    private String identity;

    private Cursor cursor;

    public BaseContactQuery(Context context, Cursor cursor, String identity) {
        this.context = context;
        this.identity = identity;
        this.cursor = cursor;
    }

    ContentResolver getCR() {
        return context.getContentResolver();
    }


    @Override
    public CEmail getEmail() {
        String[] SELECTION_ARGS = new String[]{ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE, identity};

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

        return null;
    }

    @Override
    public CPhone getPhone() {

        String WHERE_CLAUSE = ContactsContract.Data.MIMETYPE + " = ? and " + ContactsContract.Data.CONTACT_ID + " = ?";

        String PROJECTIONS[] = new String[]{ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.NUMBER};

        Cursor pCursor = getCR().query(ContactsContract.Data.CONTENT_URI, PROJECTIONS,
                WHERE_CLAUSE,
                new String[]{ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE, identity}, null);

        if (pCursor != null) {

            List<String> home = new ArrayList<>();
            List<String> work = new ArrayList<>();
            List<String> mobile = new ArrayList<>();

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

            CPhone cPhone = new CPhone(work, home, mobile);
            return cPhone;

        }

        return null;
    }

    @Override
    public CAccount getAccount() {

        String WHERE_CLAUSE = ContactsContract.RawContacts._ID + " =? ";

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

        return null;
    }

    @Override
    public CPostBoxCity getPostCode() {
        String WHERE_CLAUSE = ContactsContract.Data.MIMETYPE + " = ? and " + ContactsContract.Data.CONTACT_ID + " = ?";

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

        return null;
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
        String familyName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME));
        String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME));
        String givenName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));

        cName.setFamilyName(familyName);
        cName.setDisplayName(displayName);
        cName.setGivenName(givenName);

        return cName;
    }

    @Override
    public COrganisation getOrg() {
        String orgWhere = ContactsContract.Data.MIMETYPE + " = ? and " + ContactsContract.Data.CONTACT_ID + "= ?";
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
        }

        return null;
    }

    @Override
    public CEvents getEvents() {
        String WHERE_CLAUSE = ContactsContract.Data.MIMETYPE + " = ? and " + ContactsContract.Data.CONTACT_ID + " = ?";

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
        }

        return null;
    }

    @Override
    public CGroups getGroups() {
        String WHERE = ContactsContract.Data.MIMETYPE + " = ? and " + ContactsContract.Data.CONTACT_ID + "= ?";
        String[] WHERE_ARGS = new String[]{ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE, identity};
        String[] PROJECTIONS = new String[]{ContactsContract.Groups._ID, ContactsContract.CommonDataKinds.GroupMembership.DISPLAY_NAME};

        Cursor groups_cursor = getCR().query(
                ContactsContract.Data.CONTENT_URI,
                PROJECTIONS, WHERE, WHERE_ARGS, null);

        if (groups_cursor != null) {

            List<CGroups.BaseGroups> baseGroupsList = new ArrayList<>();

            while (groups_cursor.moveToNext()) {
                int id = groups_cursor.getInt(groups_cursor.getColumnIndex(ContactsContract.Groups._ID));
                String title = groups_cursor.getString(groups_cursor.getColumnIndex(ContactsContract.CommonDataKinds.GroupMembership.DISPLAY_NAME));

                baseGroupsList.add(new CGroups.BaseGroups(id, title));
            }

            groups_cursor.close();

            return new CGroups(baseGroupsList);

        }

        return null;
    }

    @Override
    public String getPhotoUri() {
        ContentResolver contentResolver = getCR();

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
                ContactsContract.Contacts.Photo.CONTENT_DIRECTORY).toString();
    }
}
