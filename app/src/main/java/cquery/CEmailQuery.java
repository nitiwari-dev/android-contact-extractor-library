package cquery;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

import com.bbmyjio.contactextractor.cmodels.CEmail;

/**
 *
 * Created by Nitesh on 04-04-2017.
 */

public class CEmailQuery extends BaseCQuery {

    private static final String TAG = CEmailQuery.class.getSimpleName();

    public String identity;

    public CEmailQuery(Context context, String identity) {
        super(context);
        this.identity = identity;
    }

    public CEmail getEmail() {

        String[] SELECTION_ARGS = new String[]{ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE, identity};

        String SELECTION = ContactsContract.Data.MIMETYPE + " = ? and " + ContactsContract.Data.CONTACT_ID + " = ?";

        Cursor emailCursor = getCR().query(ContactsContract.Data.CONTENT_URI, null,
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

}
