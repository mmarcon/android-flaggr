package com.here.flaggr.intent;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.here.flaggr.Flaggr;

/**
 * User: mmarcon
 * Date: 17/03/2014
 * Time: 16:54
 */
public class FlaggrIntent {

    private static final String FLAGGR_INTENT_NS = FlaggrIntent.class.getCanonicalName();

    public static final String ACTION_FLAGGR = FLAGGR_INTENT_NS + ".ACTION_FLAGGR";
    public static final String ACTION_FLAGGR_FLAG_NAME = FLAGGR_INTENT_NS + ".ACTION_FLAGGR_FLAG_NAME";
    public static final String ACTION_FLAGGR_FLAG_VALUE = FLAGGR_INTENT_NS + ".ACTION_FLAGGR_FLAG_VALUE";

    private Intent mIntent;
    private Context mContext;

    public FlaggrIntent(Context context, Intent intent) {
        mIntent = intent;
        mContext = context;
    }

    public void parse() {
        String action = mIntent.getAction();

        if(!action.equals(ACTION_FLAGGR)) {
            return;
        }

        String flagName = mIntent.getStringExtra(ACTION_FLAGGR_FLAG_NAME);
        boolean flagValue = mIntent.getBooleanExtra(ACTION_FLAGGR_FLAG_VALUE, false);

        if(TextUtils.isEmpty(flagName)) {
            return;
        }

        if(flagValue) {
            Flaggr.with(mContext).enable(flagName);
        } else {
            Flaggr.with(mContext).disable(flagName);
        }
    }
}
