package com.bbmyjio.contactextractor.contacts.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.bbmyjio.contactextractor.BuildConfig;

import com.bbmyjio.contactextractor.contacts.utils.JioContactConstants;


/**
 * Contacts Provider to access and store the contact in local DB
 * <p>
 * Created by Nitesh on 20/01/2017.
 */
public class JioContactsProvider extends ContentProvider {

    public static final String TAG = JioContactsProvider.class.getSimpleName();

    private Context mContext;

    static String DATABASE_NAME = "jiocontactsDB.db";

    static int DATA_BASE_VERSION = 1;

    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".jiocontacts";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final int ALL_CONTACTS = 1;

    private static final UriMatcher URI_MATCHER;

    public static final String QUERY_PARAMETER_LIMIT = "LIMIT";


    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(AUTHORITY, JioContactsColumns.TABLE_NAME, ALL_CONTACTS);
    }

    public SQLiteDatabase mDatabase;

    @Override
    public boolean onCreate() {
        mContext = getContext();
        JioContactsSQLiteHelper mHelper = new JioContactsSQLiteHelper(mContext, DATABASE_NAME, null,
                DATA_BASE_VERSION);
        try {
            mDatabase = mHelper.getWritableDatabase();
        } catch (Exception ex) {
            mDatabase = mHelper.getReadableDatabase();
        }
        return true;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {

        int count = 0;
        switch (URI_MATCHER.match(uri)) {
            case ALL_CONTACTS:
                for (ContentValues value : values) {
                    if (mDatabase.insert(JioContactsColumns.TABLE_NAME, null, value) != -1) {
                        count++;
                    }
                }
                break;

            default:
                break;
        }
        Log.d(TAG, "BULK Inserted rows: " + count);
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return (count > 0 ? count : super.bulkInsert(uri, values));
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;

        String limit = uri.getQueryParameter(QUERY_PARAMETER_LIMIT);

        switch (URI_MATCHER.match(uri)) {
            case ALL_CONTACTS:

                if (!TextUtils.isEmpty(limit))
                    cursor = mDatabase.query(JioContactsColumns.TABLE_NAME, projection,
                            selection, selectionArgs, null, null, sortOrder, limit);
                else
                    cursor = mDatabase.query(JioContactsColumns.TABLE_NAME, projection,
                            selection, selectionArgs, null, null, sortOrder);

                break;

            default:
                break;
        }
        if (cursor != null) {
            cursor.setNotificationUri(mContext.getContentResolver(), uri);
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowId = -1;
        Uri insertUri = null;
        switch (URI_MATCHER.match(uri)) {
            case ALL_CONTACTS:
                rowId = mDatabase.insert(JioContactsColumns.TABLE_NAME, null, values);
                insertUri = Uri.withAppendedPath(JioContactsColumns.CONTENT_URI,
                        Long.toString(rowId));
                break;
        }
        if (rowId != -1) {
            Log.d(TAG, "Inserted rowId: " + rowId);
            mContext.getContentResolver().notifyChange(uri, null);
        }
        return insertUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int numRows = 0;
        switch (URI_MATCHER.match(uri)) {
            case ALL_CONTACTS:
                numRows = mDatabase.delete(JioContactsColumns.TABLE_NAME, selection,
                        selectionArgs);
                break;
        }
        return numRows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int numRows = 0;
        switch (URI_MATCHER.match(uri)) {
            case ALL_CONTACTS:
                numRows = mDatabase.update(JioContactsColumns.TABLE_NAME, values,
                        selection, selectionArgs);
                break;
        }
        Log.d(TAG, "Updated rows: " + numRows);
        return numRows;
    }

    public static class JioContactsSQLiteHelper extends SQLiteOpenHelper {

        static JioContactsSQLiteHelper sInstance;


        public static JioContactsSQLiteHelper getInstance(Context context) {
            if (sInstance == null) {
                sInstance = new JioContactsSQLiteHelper(context.getApplicationContext());
            }
            return sInstance;
        }

        public JioContactsSQLiteHelper(Context context) {
            super(context, DATABASE_NAME, null,
                    DATA_BASE_VERSION);
        }

        public JioContactsSQLiteHelper(Context context, String name,
                                       SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            createJioContacts(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }

        void createJioContacts(SQLiteDatabase db) {
            db.execSQL(JioContactsColumns.CREATE_TABLE);
        }

    }

    public static class JioContactsColumns extends JioContactConstants {

        public static String TABLE_NAME = "jioc";

        public static final String DEFAULT_TEXT = " DEFAULT \'NA\' ";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(
                JioContactsProvider.CONTENT_URI, TABLE_NAME);

        public static final String CREATE_TABLE = "create table IF NOT EXISTS " + TABLE_NAME + " ( "
                + COMMON.RELATION + " text" + ", "
                + COMMON.IDENTITY + " integer primary key" + ","
                + COMMON.FAVORITES + " text" + DEFAULT_TEXT + ","
                + PHONE.HOME + " text" + DEFAULT_TEXT + ", "
                + PHONE.WORK + " text" + DEFAULT_TEXT + ", "
                + PHONE.MOBILE + " text" + DEFAULT_TEXT + ", "
                + NAME.DISPLAY_NAME + " text" + DEFAULT_TEXT + ", "
                + NAME.FAMILY_NAME + " text" + DEFAULT_TEXT + ", "
                + NAME.GIVEN_NAME + " text" + DEFAULT_TEXT + ", "
                + EMAIL.WORK + " text" + DEFAULT_TEXT + ", "
                + EMAIL.HOME + " text" + DEFAULT_TEXT + ", "
                + POSTAL.POSTAL_CODE + " integer" + DEFAULT_TEXT + ", "
                + POSTAL.CITY + " text" + DEFAULT_TEXT + ", "
                + ORGANISATION.COMPANY + " text" + DEFAULT_TEXT + ", "
                + ORGANISATION.DEPARTMENT + " text" + DEFAULT_TEXT + ", "
                + EVENTS.BIRTH + " text" + DEFAULT_TEXT + ", "
                + EVENTS.ANNIVESARY + " text" + DEFAULT_TEXT + ", "
                + ACCOUNT.ACCOUNT_INFO + " text" + DEFAULT_TEXT + " )";
    }

}
