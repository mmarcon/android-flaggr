package com.here.flaggr;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import com.here.flaggr.internal.Flag;

import java.util.HashMap;
import java.util.Map;

/**
 * The main Flaggr class.
 *
 * Exposes all the methods that are needed to get and set
 * feature flags.
 *
 * User: mmarcon
 * Date: 14/03/2014
 * Time: 15:09
 */
public final class Flaggr {

    private static final String TAG = Flaggr.class.getSimpleName();

    protected static final String FLAGGR_SHARED_PREFERENCES_FILENAME = "com.here.flaggr.SHARED_PREFERENCES_FILENAME";
    protected static final String FLAGGR_SHARED_PREFERENCES_PREFIX = "FLAGGR_ID_";

    public static final String FLAGGR_OVERRIDABLE_ATTRIBUTE_NAME = "overridable";

    /**
     * The Flaggr singleton
     */
    private static Flaggr FLAGGR = null;

    /**
     * I need a Context to access resources
     * and shared preferences.
     */
    private Context mContext;

    private Map<Integer, Flag> mFlaggrStore = new HashMap<Integer, Flag>();

    protected Flaggr(Context context) {
        mContext = context;
    }

    /**
     * Global, default {@link Flaggr} instance generator.
     *
     * @param context some kind of {@link android.content.Context} used for accessing
     *                resources and shared preferences.
     * @return the Flaggr instance.
     */
    public static Flaggr with(Context context) {
        if(FLAGGR == null) {
            FLAGGR = new Flaggr(context);
        }
        return FLAGGR;
    }

    private Flag getFlag(int flagId) {
        //Do I have it already in cache?
        if(mFlaggrStore.containsKey(flagId)) {
            return mFlaggrStore.get(flagId);
        }

        Resources resources = mContext.getResources();
        boolean value = resources.getBoolean(flagId);

        XmlResourceParser xml = resources.getXml(flagId);
        //Uhmm:
        String namespace = xml.getNamespace();
        boolean overridable = xml.getAttributeBooleanValue(namespace, FLAGGR_OVERRIDABLE_ATTRIBUTE_NAME, true);

        if(overridable) {
            //If the value is overridable, check if a different value is provided in shared preferences
            value = getPreferences().getBoolean(getPreferenceName(flagId), value);
        }

        Flag flag = new Flag(value, overridable);

        //Cache the flag for later
        mFlaggrStore.put(flagId, flag);

        return flag;

    }

    private String getPreferenceName(int flagId) {
        return FLAGGR_SHARED_PREFERENCES_PREFIX + flagId;
    }

    private SharedPreferences getPreferences() {
        return mContext.getSharedPreferences(FLAGGR_SHARED_PREFERENCES_FILENAME, Context.MODE_PRIVATE);
    }

    public boolean isOverridable(int flagId) {
        return getFlag(flagId).isOverridable();
    }

    public boolean isEnabled(int flagId) {
        return getFlag(flagId).getValue();
    }

    public void enable(int flagId) {
        if(getFlag(flagId).isOverridable()) {
            getFlag(flagId).setValue(true);
            //also, write override into shared preferences
            getPreferences().edit().putBoolean(getPreferenceName(flagId), true).commit();
            return;
        }
        throw new FlagNotOverridableException(flagId);
    }

    public void disable(int flagId) {
        if(getFlag(flagId).isOverridable()) {
            getFlag(flagId).setValue(false);
            getPreferences().edit().putBoolean(getPreferenceName(flagId), true).commit();
            return;
        }
        throw new FlagNotOverridableException(flagId);
    }
}
