package contacts.services;

import android.app.IntentService;
import android.content.Intent;

import contacts.query.JioContactQuery;

/**
 * Intent Service to fetch the contact and store into the local db
 *
 * Created by Nitesh on 21-01-2017.
 */

public class JioContactIntentService extends IntentService {


    private static final String TAG = JioContactIntentService.class.getSimpleName();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public JioContactIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        new JioContactQuery(this).insertContactList();
    }
}
