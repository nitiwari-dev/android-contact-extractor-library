package cquery;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.List;

import contacts.query.BaseJioQuery;
import contacts.utils.JioContactsDBHelper;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

/**
 * Created by Nitesh on 03-04-2017.
 */

public abstract class BaseCQuery {

    private static final String TAG = BaseCQuery.class.getSimpleName();

    private Context context;

    private String identity;

    protected BaseCQuery(Context context){
        this(context, null);
    }

    protected BaseCQuery(Context context, String identity) {
        this.context = context;
        this.identity = identity;
    }

    ContentResolver getCR() {
        return context.getContentResolver();
    }


    protected void getName() {
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


                Log.d(TAG, "|Family|" + familyName + "displayName" + displayName + "|givent|" + givenName);
            }
            orgCur.close();
        }

    }


}
