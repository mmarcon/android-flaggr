package com.here.flaggr.intent;

import android.content.Intent;
import android.net.Uri;

/**
 * User: mmarcon
 * Date: 17/03/2014
 * Time: 16:54
 */
public class FlaggrIntent {

    //Expecting something like:
    //flaggr://new_awesome_feature/enable
    //flaggr://new_awesome_feature/disable
    public static final String SCHEME = "flaggr";

    private Intent mIntent;

    public FlaggrIntent(Intent intent) {
        mIntent = intent;
    }

    public void parse() {
        String action = mIntent.getAction();

        if(!action.equals(Intent.ACTION_DEFAULT)) {
            return;
        }

        Uri data = mIntent.getData();
        if(!data.getScheme().equals(SCHEME)) {
            return;
        }
        String feature = data.getHost();
        String enable = data.getLastPathSegment();
    }
}
