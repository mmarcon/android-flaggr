package com.here.flaggr.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.here.flaggr.intent.FlaggrIntent;

/**
 * User: mmarcon
 * Date: 27/03/2014
 * Time: 16:45
 */
public class FlaggrReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        FlaggrIntent flaggrIntent = new FlaggrIntent(context, intent);
        flaggrIntent.parse();
    }
}
