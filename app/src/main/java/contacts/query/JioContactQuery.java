package contacts.query;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import contacts.model.api.Account;
import contacts.model.api.ContactsInfo;
import contacts.model.api.Email;
import contacts.model.api.Events;
import contacts.model.api.Item;
import contacts.model.api.Name;
import contacts.model.api.Oraganisation;
import contacts.model.api.CPhone;
import contacts.model.api.Postal;
import contacts.model.api.Type;
import contacts.provider.JioContactsProvider;
import contacts.utils.JioContactsDBHelper;

/**
 * Make Query to Contacts URI
 * <p>
 * Created by Nitesh on 20-01-2017.
 */

public class JioContactQuery extends BaseJioQuery {

    private static final String TAG = JioContactQuery.class.getSimpleName();
    private Context mContext;

    public JioContactQuery(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }

    /**
     * InsertContactList
     */
    public void insertContactList() {

        ContentResolver cr = getCR();
        Cursor fetchCursor = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if (fetchCursor != null && fetchCursor.getCount() > 0) {

            long startTime = System.currentTimeMillis();
            Log.d(TAG, "|-----------START TIME-----------------|");
            while (fetchCursor.moveToNext()) {

                int identity = fetchCursor.getInt(fetchCursor.getColumnIndex(ContactsContract.Contacts._ID));

                if (Integer.parseInt(fetchCursor.getString(fetchCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Log.d(TAG, "|identity|"  + identity);

                    new JioContactsDBHelper(mContext).insertLogHelper(getDBContactsInfo(identity));
                }
            }

            Log.d(TAG, "|-----------END TIME-----------------|" + (System.currentTimeMillis() - startTime / 1000) + " Sec");
        }

    }

    /**
     * FetchContactQuery
     *
     * @param LIMIT
     * @return
     */

    public List<ContactsInfo> fetchContactQuery(String LIMIT) {
        //return fetchContactQuery(LIMIT, null);

        return null;
    }


    /**
     * Fetch all the contact with limit and contactIdentity i.e contactId
     *
     * @param LIMIT
     * @param contactIdentity
     * @return
     */

   /* public List<ContactsInfo> fetchContactQuery(String LIMIT, String contactIdentity) {
        Uri CONTENT_URI = JioContactsProvider.JioContactsColumns.CONTENT_URI;

        if (!TextUtils.isEmpty(LIMIT)) {
            CONTENT_URI = CONTENT_URI.buildUpon().appendQueryParameter(JioContactsProvider.QUERY_PARAMETER_LIMIT, LIMIT).build();
        }

        Cursor fetchCursor;

        if (contactIdentity != null) {

            String where = COMMON.IDENTITY + "> ?";

            String whereArgs[] = new String[]{contactIdentity};

            fetchCursor = getCR().query(CONTENT_URI,
                    null, where, whereArgs, COMMON.IDENTITY);

        } else {
            fetchCursor = getCR().query(CONTENT_URI,
                    null, null, null, COMMON.IDENTITY);
        }


        if (fetchCursor != null && fetchCursor.getCount() > 0) {

            long startTime = System.currentTimeMillis();

            Log.d(TAG, "|-----Fetch------START TIME-----------------|");

            List<ContactsInfo> contactsInfos = new ArrayList<>();

            while (fetchCursor.moveToNext()) {

                int columnIndexFav = fetchCursor.getColumnIndex(COMMON.FAVORITES);
                int columnIndexIdentity = fetchCursor.getColumnIndex(COMMON.IDENTITY);
                int columnIndexRelation = fetchCursor.getColumnIndex(COMMON.RELATION);

                int columnIndexHomePhone = fetchCursor.getColumnIndex(PHONE.HOME);
                int columnIndexMobilePhone = fetchCursor.getColumnIndex(PHONE.MOBILE);
                int columnIndexMobileWork = fetchCursor.getColumnIndex(PHONE.WORK);


                int columnIndexHomeEmail = fetchCursor.getColumnIndex(EMAIL.HOME);
                int columnIndexWorkEmail = fetchCursor.getColumnIndex(EMAIL.WORK);

                int columnIndexDisplayName = fetchCursor.getColumnIndex(NAME.DISPLAY_NAME);
                int columnIndexFamilyName = fetchCursor.getColumnIndex(NAME.FAMILY_NAME);
                int columnIndexGivenName = fetchCursor.getColumnIndex(NAME.GIVEN_NAME);

                int columnIndexPostCode = fetchCursor.getColumnIndex(POSTAL.POSTAL_CODE);
                int columnIndexPostCity = fetchCursor.getColumnIndex(POSTAL.CITY);

                int columnIndexCompany = fetchCursor.getColumnIndex(ORGANISATION.COMPANY);
                int columnIndexDepartment = fetchCursor.getColumnIndex(ORGANISATION.DEPARTMENT);

                int columnIndexBirth = fetchCursor.getColumnIndex(EVENTS.BIRTH);
                int columnIndexAnniversary = fetchCursor.getColumnIndex(EVENTS.ANNIVESARY);

                int columnIndexAccInfo = fetchCursor.getColumnIndex(ACCOUNT.ACCOUNT_INFO);

                ContactsInfo contactsInfo = new ContactsInfo();

                String fav = fetchCursor.getString(columnIndexFav);
                contactsInfo.setFavorites(fav);

                String identity = fetchCursor.getString(columnIndexIdentity);
                contactsInfo.setIdentity(identity);

                String relation = fetchCursor.getString(columnIndexRelation);
                contactsInfo.setRelation(relation);

                //PHONE
                CPhone phone = new CPhone();
                phone.setHome(fetchCursor.getString(columnIndexHomePhone));
                phone.setMobile(fetchCursor.getString(columnIndexMobilePhone));
                phone.setWork(fetchCursor.getString(columnIndexMobileWork));
                contactsInfo.setPhone(phone);

                //EMAIL
                Type type = new Type();
                type.setHome(fetchCursor.getString(columnIndexHomeEmail));
                type.setWork(fetchCursor.getString(columnIndexWorkEmail));

                Email email = new Email();
                email.setType(type);
                contactsInfo.setEmail(email);

                //NAME
                Name name = new Name();
                name.setDisplayName(fetchCursor.getString(columnIndexDisplayName));
                name.setFamilyName(fetchCursor.getString(columnIndexFamilyName));
                name.setGivenName(fetchCursor.getString(columnIndexGivenName));
                contactsInfo.setName(name);

                //POSTAL
                Postal postal = new Postal();
                postal.setCity(fetchCursor.getString(columnIndexPostCity));
                postal.setPostCode(fetchCursor.getString(columnIndexPostCode));
                contactsInfo.setPostal(postal);

                //ORG
                Oraganisation oraganisation = new Oraganisation();
                oraganisation.setCompany(fetchCursor.getString(columnIndexCompany));
                oraganisation.setDepartment(fetchCursor.getString(columnIndexDepartment));
                contactsInfo.setOraganisation(oraganisation);

                //EVENTs
                Events events = new Events();
                events.setAnniversary(fetchCursor.getString(columnIndexAnniversary));
                events.setBirthDay(fetchCursor.getString(columnIndexBirth));
                contactsInfo.setEvents(events);

                //ACCOUNT
                String accInfo = fetchCursor.getString(columnIndexAccInfo);

                if (!TextUtils.isEmpty(accInfo)) {
                    try {
                        JSONArray jsonArray = new JSONArray(accInfo);

                        if (jsonArray != null && jsonArray.length() > 0) {

                            Account account = new Account();
                            Item item[] = new Item[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                item[i] = new Item();
                                JSONObject object = jsonArray.optJSONObject(i);
                                item[i].setName(object.optString("name"));
                                item[i].setType(object.optString("type"));
                            }

                            account.setItem(item);

                            contactsInfo.setAccount(account);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                contactsInfos.add(contactsInfo);
            }


            Log.d(TAG, "|------Fetch-----END TIME-----------------|" + (System.currentTimeMillis() - startTime / 1000) + " Sec");

            return contactsInfos;
        }

        return null;

    }*/

    /**
     * Fetch all the contactInfo
     *
     * @return
     */
    public List<ContactsInfo> fetchContactQuery() {
        return fetchContactQuery(null);
    }

}
