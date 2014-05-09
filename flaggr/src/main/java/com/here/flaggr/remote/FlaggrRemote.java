package com.here.flaggr.remote;

import android.content.Context;
import com.here.flaggr.Flaggr;

/**
 * Created by mmarcon on 09/05/2014.
 */
public class FlaggrRemote {

    /**
     * The FlaggrRemote singleton.
     */
    private static FlaggrRemote FLAGGR_REMOTE = null;

    /**
     * I need a Context to access ...
     */
    private Context mContext;

    private Flaggr mFlaggr;

    protected FlaggrRemote(Context context) {
        mContext = context;
        mFlaggr = Flaggr.with(context);
    }

    public static synchronized FlaggrRemote with(Context context) {
        if(FLAGGR_REMOTE == null) {
            FLAGGR_REMOTE = new FlaggrRemote(context);
        }
        return FLAGGR_REMOTE;
    }
}
