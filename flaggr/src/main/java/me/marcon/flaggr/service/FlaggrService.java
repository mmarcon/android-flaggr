package me.marcon.flaggr.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import me.marcon.flaggr.Flaggr;
import me.marcon.flaggr.receiver.FlaggrReceiver;

/**
 * User: mmarcon
 * Date: 5/23/14
 * Time: 9:40 PM
 */
public class FlaggrService extends IntentService {

    private static final String TAG = FlaggrService.class.getCanonicalName();

    public FlaggrService() {
        super(FlaggrService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(!intent.hasExtra(FlaggrReceiver.FLAGGR_RECEIVER_FLAG_KEY_INTENT_EXTRA) ||
                !intent.hasExtra(FlaggrReceiver.FLAGGR_RECEIVER_FLAG_VALUE_INTENT_EXTRA)) {
            Log.w(TAG, "Received intent but no flag name or flag value was specified: returning");
            FlaggrReceiver.completeWakefulIntent(intent);
            return;
        }
        String flagName = intent.getStringExtra(FlaggrReceiver.FLAGGR_RECEIVER_FLAG_KEY_INTENT_EXTRA);
        boolean flagValue = intent.getBooleanExtra(FlaggrReceiver.FLAGGR_RECEIVER_FLAG_VALUE_INTENT_EXTRA, true);

        if(flagValue) {
            Log.d(TAG, String.format("Enabling %s", flagName));
            Flaggr.with(this).enable(flagName);
        } else {
            Log.d(TAG, String.format("Disabling %s", flagName));
            Flaggr.with(this).disable(flagName);
        }

        FlaggrReceiver.completeWakefulIntent(intent);
    }
}
