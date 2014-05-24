package me.marcon.flaggr;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import me.marcon.flaggr.internal.Flag;

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

    protected static final String FLAGGR_SHARED_PREFERENCES_FILENAME = "me.marcon.flaggr.SHARED_PREFERENCES_FILENAME";
    protected static final String FLAGGR_SHARED_PREFERENCES_PREFIX = "FLAGGR_ID_";

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
    public static synchronized Flaggr with(Context context) {
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

        String resourceName = resources.getResourceEntryName(flagId);
        boolean overridable = resourceName.startsWith("_");

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

    /**
     * Checks if the value of a flag can be overridden.
     *
     * @param flagId the flag id (e.g. R.id.my_awesome_flag)
     * @return true if the value can be overridden, false otherwise.
     */
    public boolean isOverridable(int flagId) {
        return getFlag(flagId).isOverridable();
    }

    /**
     * Check if the flag is enabled.
     *
     * @param flagId the flag id (e.g. R.id.my_awesome_flag)
     * @return true if the value for the flag is true.
     */
    public boolean isEnabled(int flagId) {
        return getFlag(flagId).getValue();
    }

    /**
     * If the flag is overridable, it overrides its value
     * with true. Note that a non-overridable flag must be considered
     * as a final variable, i.e. setting it to true when it is already
     * true will throw an exception.
     *
     * The new value is saved in shared preferences.
     *
     * @param flagId the flag id (e.g. R.id.my_awesome_flag)
     * @throws FlagNotOverridableException if the flag is not overridable
     */
    public void enable(int flagId) {
        if(getFlag(flagId).isOverridable()) {
            getFlag(flagId).setValue(true);
            //also, write override into shared preferences
            getPreferences().edit().putBoolean(getPreferenceName(flagId), true).commit();
            return;
        }
        throw new FlagNotOverridableException(flagId);
    }

    public void enable(String stringIdentifier) {
        int flagId = getFlagId(stringIdentifier);
        if(flagId == 0) {
            throw new FlagNotFoundException(stringIdentifier);
        }
        enable(flagId);
    }

    /**
     * If the flag is overridable, it overrides its value
     * with false. Note that a non-overridable flag must be considered
     * as a final variable, i.e. setting it to false when it is already
     * false will throw an exception.
     *
     * The new value is saved in shared preferences.
     *
     * @param flagId the flag id (e.g. R.id.my_awesome_flag)
     * @throws FlagNotOverridableException if the flag is not overridable
     */
    public void disable(int flagId) {
        if(getFlag(flagId).isOverridable()) {
            getFlag(flagId).setValue(false);
            getPreferences().edit().putBoolean(getPreferenceName(flagId), false).commit();
            return;
        }
        throw new FlagNotOverridableException(flagId);
    }

    public void disable(String stringIdentifier) {
        int flagId = getFlagId(stringIdentifier);
        if(flagId == 0) {
            throw new FlagNotFoundException(stringIdentifier);
        }
        disable(flagId);
    }

    protected int getFlagId(String stringIdentifier) {
        return mContext.getResources().getIdentifier(stringIdentifier , "bool", mContext.getPackageName());
    }
}
