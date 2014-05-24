package me.marcon.flaggr.receiver;

import android.support.v4.content.WakefulBroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import me.marcon.flaggr.Flaggr;
import me.marcon.flaggr.service.FlaggrService;

/**
 * User: mmarcon
 * Date: 27/03/2014
 * Time: 16:45
 *
 * Expose this with:
 *
 * <receiver android:name="me.marcon.flaggr.receiver.FlaggrReceiver" >
 *     <intent-filter>
 *         <action android:name="me.marcon.flaggr.receiver.FLAGGR_RECEIVER_DEFAULT_INTENT_ACTION" />
 *     </intent-filter>
 * </receiver>
 *
 */
public class FlaggrReceiver extends WakefulBroadcastReceiver {

    private static final String TAG = FlaggrReceiver.class.getCanonicalName();

    private static final String NS = "me.marcon.flaggr.receiver";
    public static final String FLAGGR_RECEIVER_DEFAULT_INTENT_ACTION = NS + ".FLAGGR_RECEIVER_DEFAULT_INTENT_ACTION";
    public static final String FLAGGR_RECEIVER_FLAG_KEY_INTENT_EXTRA = NS + ".FLAGGR_RECEIVER_FLAG_KEY_INTENT_EXTRA";
    public static final String FLAGGR_RECEIVER_FLAG_VALUE_INTENT_EXTRA = NS + ".FLAGGR_RECEIVER_FLAG_VALUE_INTENT_EXTRA";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(action.equals(FLAGGR_RECEIVER_DEFAULT_INTENT_ACTION)) {
            if(!intent.hasExtra(FLAGGR_RECEIVER_FLAG_KEY_INTENT_EXTRA) || !intent.hasExtra(FLAGGR_RECEIVER_FLAG_VALUE_INTENT_EXTRA)) {
                Log.w(TAG, "Received intent but no flag name or flag value was specified: returning");
                return;
            }
            String flagName = intent.getStringExtra(FLAGGR_RECEIVER_FLAG_KEY_INTENT_EXTRA);
            boolean flagValue = intent.getBooleanExtra(FLAGGR_RECEIVER_FLAG_VALUE_INTENT_EXTRA, true);
            Log.d(TAG, String.format("Received intent with flag %s set to value %b", flagName, flagValue));

            Intent wakefulServiceIntent = new Intent(context, FlaggrService.class);
            wakefulServiceIntent.putExtra(FLAGGR_RECEIVER_FLAG_KEY_INTENT_EXTRA, flagName);
            wakefulServiceIntent.putExtra(FLAGGR_RECEIVER_FLAG_VALUE_INTENT_EXTRA, flagValue);
            startWakefulService(context, wakefulServiceIntent);
        }
    }
}
