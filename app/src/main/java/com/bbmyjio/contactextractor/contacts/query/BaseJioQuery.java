package com.bbmyjio.contactextractor.contacts.query;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import com.bbmyjio.contactextractor.contacts.model.db.JioContactModel;
import com.google.myjson.Gson;
import com.google.myjson.GsonBuilder;
import com.google.myjson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import com.bbmyjio.contactextractor.contacts.model.db.JioBaseAccountModel;
import com.bbmyjio.contactextractor.contacts.utils.JioContactConstants;

/**
 * BaseJioQuery for Fetching the query
 * <p>
 * Created by Nitesh on 21-01-2017.
 */

abstract public class BaseJioQuery extends JioContactConstants {

    private static final String TAG = BaseJioQuery.class.getSimpleName();

    private Context context;

    private JioContactModel jioContactModel;

    protected String WHERE_CLAUSE = ContactsContract.Data.MIMETYPE + " = ? and " + ContactsContract.Data.CONTACT_ID + " = ?";

    private String identity;

    protected BaseJioQuery(Context context) {
        this.context = context;
    }

    protected ContentResolver getCR() {
        return context.getContentResolver();
    }


    private void getEvents() {

        String whereArgs[] = new String[]{ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE, identity};
        Cursor cursorPostcode = getCR().query(ContactsContract.Data.CONTENT_URI, null, WHERE_CLAUSE, whereArgs, null);

        if (cursorPostcode != null) {

            List<String> typeAnni = new ArrayList<>();
            List<String> typeBirth = new ArrayList<>();

            while (cursorPostcode.moveToNext()) {
                String startData = cursorPostcode.getString(
                        cursorPostcode.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));

                int type = cursorPostcode.getInt(
                        cursorPostcode.getColumnIndex(ContactsContract.CommonDataKinds.Event.TYPE));

                switch (type) {
                    case ContactsContract.CommonDataKinds.Event.TYPE_ANNIVERSARY:
                        Log.d(TAG, "TYPE_ANNIVERSARY" + startData);
                        typeAnni.add(startData);
                        break;

                    case ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY:
                    default:
                        Log.d(TAG, "TYPE_BIRTHDAY" + startData);
                        typeBirth.add(startData);
                        break;
                }


                Log.d(TAG, "|StartDate|" + startData + "|TYPE|" + type);
            }

            jioContactModel.setAnnv_event(TextUtils.join(",", typeAnni));
            jioContactModel.setBirth_event(TextUtils.join(",", typeBirth));

            typeAnni.clear();
            typeBirth.clear();


            cursorPostcode.close();
        }
    }

    private void getAccount() {

        String WHERE_CLAUSE = ContactsContract.RawContacts._ID + " =? ";

        String whereArgs[] = new String[]{identity};
        Cursor cursorPostcode = getCR().query(ContactsContract.RawContacts.CONTENT_URI, null, WHERE_CLAUSE, whereArgs, null);

        if (cursorPostcode != null) {

            JioBaseAccountModel jioBaseAccountModel = new JioBaseAccountModel();

            List<JioBaseAccountModel.JioAccountModel> jioList = jioBaseAccountModel.jioAccountModel;

            while (cursorPostcode.moveToNext()) {

                String accountName = cursorPostcode.getString(
                        cursorPostcode.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_NAME));

                String accountType = cursorPostcode.getString(
                        cursorPostcode.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_TYPE));

                String _id = cursorPostcode.getString(
                        cursorPostcode.getColumnIndex(ContactsContract.RawContacts._ID));

                jioList.add(new JioBaseAccountModel.JioAccountModel(accountType, accountName));

                Log.d(TAG, "|Account|" + accountName + "|Account Type|" + accountType + "|_Id|" + _id);
            }

            Gson gson = new GsonBuilder().create();

            String accountInfo = gson.toJson(jioList, new TypeToken<ArrayList<JioBaseAccountModel.JioAccountModel>>() {
            }.getType());

            jioContactModel.setAccount_info(accountInfo);

            cursorPostcode.close();
        }
    }

    private void getPostCode() {
        String whereArgs[] = new String[]{ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE, identity};
        Cursor cursorPostcode = getCR().query(ContactsContract.Data.CONTENT_URI, null, WHERE_CLAUSE, whereArgs, null);

        if (cursorPostcode != null) {

            while (cursorPostcode.moveToNext()) {
                String poBox = cursorPostcode.getString(
                        cursorPostcode.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));

                String city = cursorPostcode.getString(
                        cursorPostcode.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));

                jioContactModel.setPostal_code(poBox);
                jioContactModel.setCity(city);


                Log.d(TAG, "|POSTAL CODE|" + poBox + "|CITY|" + city);
            }

            cursorPostcode.close();
        }

    }

    private void getPhone() {
        Cursor pCursor = getCR().query(ContactsContract.Data.CONTENT_URI, null,
                WHERE_CLAUSE,
                new String[]{ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE, identity}, null);

        if (pCursor != null) {

            List<String> home = new ArrayList<>();
            List<String> work = new ArrayList<>();

            List<String> mobile = new ArrayList<>();

            while (pCursor.moveToNext()) {

                int phoneType = pCursor.getInt(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                String phoneNo = pCursor.getString(pCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                Log.d(TAG, "|PhoneNumber|" + phoneNo + "|Type|" + phoneType);


                switch (phoneType) {
                    case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                        home.add(phoneNo);
                        Log.d(TAG, "Phone.TYPE_HOME " + phoneNo);
                        //jioContactModel.setHome_phone(phoneNo);
                        break;
                    case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                        Log.d(TAG, "Phone.TYPE_WORK " + phoneNo);
                        work.add(phoneNo);
                        //jioContactModel.setWork_phone(phoneNo);
                        break;
                    case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                    default:
                        Log.d(TAG, "Phone.TYPE_WORK_MOBILE " + phoneNo);
                        mobile.add(phoneNo);
                        break;
                }
            }

            jioContactModel.setHome_phone(TextUtils.join(",", home));
            jioContactModel.setWork_phone(TextUtils.join(",", work));
            jioContactModel.setMobile_phone(TextUtils.join(",", mobile));

            home.clear();
            work.clear();
            mobile.clear();

            pCursor.close();

        }
    }

    private void getGroups(){
        String[] whereNameParams = new String[]{ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE, identity};

        Cursor groups_cursor= getCR().query(
                ContactsContract.Groups.CONTENT_URI,
                new String[]{
                        ContactsContract.Groups._ID,
                        ContactsContract.Groups.TITLE
                }, null, whereNameParams, null
        );

        if (groups_cursor != null) {

            List<String> home = new ArrayList<>();
            List<String> work = new ArrayList<>();

            while (groups_cursor.moveToNext()) {
                int id = groups_cursor.getInt(groups_cursor.getColumnIndex(ContactsContract.Groups._ID));
                String title = groups_cursor.getString(groups_cursor.getColumnIndex(ContactsContract.Groups.TITLE));


                Log.d(TAG, "|TYPE|" + id + "|EMAIL|" + title);
            }

            jioContactModel.setWork_email(TextUtils.join(",", work));
            jioContactModel.setHome_email(TextUtils.join(",", home));

            work.clear();
            home.clear();

           // emailCursor.close();

        }


    }
    private void getEmail() {

        String[] whereNameParams = new String[]{ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE, identity};

        Cursor emailCursor = getCR().query(ContactsContract.Data.CONTENT_URI, null,
                WHERE_CLAUSE,
                whereNameParams,
                ContactsContract.CommonDataKinds.Email.ADDRESS);

        if (emailCursor != null) {

            List<String> home = new ArrayList<>();
            List<String> work = new ArrayList<>();

            while (emailCursor.moveToNext()) {
                int type = emailCursor.getInt(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                String data = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

                switch (type) {
                    case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                        Log.d(TAG, "Phone.TYPE_WORK_EMAIL " + type);
                        work.add(data);
                        break;

                    case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                    default:
                        Log.d(TAG, "Phone.TYPE_HOME_EMAIL " + type);
                        home.add(data);
                        break;
                }


                Log.d(TAG, "|TYPE|" + type + "|EMAIL|" + data);
            }

            jioContactModel.setWork_email(TextUtils.join(",", work));
            jioContactModel.setHome_email(TextUtils.join(",", home));

            work.clear();
            home.clear();

            emailCursor.close();

        }


    }

    private void getOrganisation() {

        String orgWhere = ContactsContract.Data.MIMETYPE + " = ? and " + ContactsContract.Data.CONTACT_ID + "= ?";
        String[] orgWhereParams = new String[]{
                ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE, identity};

        Cursor orgCur = getCR().query(ContactsContract.Data.CONTENT_URI,
                null, orgWhere, orgWhereParams, null);

        if (orgCur != null) {
            while (orgCur.moveToNext()) {
                String company = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY));
                String department = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DEPARTMENT));

                jioContactModel.setCompany(company);
                jioContactModel.setDepartment(department);

                Log.d(TAG, "|COMPANY|" + company + "DEPARTMENT" + department);
            }
            orgCur.close();
        }

    }


    private void getFavorites() {
        jioContactModel.setFavorites("1");
    }

    private void getRelation() {
        jioContactModel.setRelation("1");
    }


    protected JioContactModel getDBContactsInfo(int identity) {
        this.identity = String.valueOf(identity);

        if (jioContactModel == null)
            jioContactModel = new JioContactModel();

        jioContactModel.setIdentity(identity);
        getEmail();
        getPhone();
        getPostCode();
        getAccount();
        getEvents();
        getOrganisation();
        getFavorites();
        getRelation();
        getName();

        return jioContactModel;
    }

   /* CQuery.getInstance().limit(10).skip(20).id().getAllInfo(new ICQuer(){
        @Override

    });

    CQuery.getInstance().getAllInfo(int type, new ICQuery(){

        @Override

    })*/
    private void getName() {
        String orgWhere = ContactsContract.Data.MIMETYPE + " = ? and " + ContactsContract.Data.CONTACT_ID + "= ?";
        String[] orgWhereParams = new String[]{
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE, identity};

        Cursor orgCur = getCR().query(ContactsContract.Data.CONTENT_URI,
                null, orgWhere, orgWhereParams, null);

        if (orgCur != null) {
            while (orgCur.moveToNext()) {

                String familyName = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
                String displayName = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME));
                String givenName = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));

                jioContactModel.setFamily_name(familyName);
                jioContactModel.setDisplay_name(displayName);
                jioContactModel.setGiven_name(givenName);

                Log.d(TAG, "|Family|" + familyName + "displayName" + displayName + "|givent|" + givenName);
            }
            orgCur.close();
        }

    }
}
