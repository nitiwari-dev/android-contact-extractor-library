package cquery;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import contacts.model.api.CPhone;

/**
 * Created by Nitesh on 18-04-2017.
 */

public class CPhoneQuery extends BaseCQuery {


    private static final String TAG = CPhoneQuery.class.getSimpleName();

    protected String WHERE_CLAUSE = ContactsContract.Data.MIMETYPE + " = ? and " + ContactsContract.Data.CONTACT_ID + " = ?";

    private String identity;

    protected CPhoneQuery(Context context, String identity) {
        super(context);
        this.identity = identity;
    }

    protected CPhone getPhone() {

        Cursor pCursor = getCR().query(ContactsContract.Data.CONTENT_URI, null,
                WHERE_CLAUSE,
                new String[]{ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE, identity}, null);

        if (pCursor != null) {

            List<String> home = new ArrayList<>();
            List<String> work = new ArrayList<>();
            List<String> mobile = new ArrayList<>();

            if(pCursor.getCount() == 0){
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
}
