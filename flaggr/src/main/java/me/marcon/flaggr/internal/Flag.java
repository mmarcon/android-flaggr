package me.marcon.flaggr.internal;

/**
 * User: mmarcon
 * Date: 14/03/2014
 * Time: 15:34
 */
public class Flag {
    protected boolean mValue = false;
    protected boolean mOverridable = true;

    public Flag(boolean value, boolean overridable) {
        this.mValue = value;
        this.mOverridable = overridable;
    }

    public boolean getValue() {
        return mValue;
    }

    public void setValue(boolean mValue) {
        this.mValue = mValue;
    }

    public boolean isOverridable() {
        return mOverridable;
    }

    public void setOverridable(boolean mOverridable) {
        this.mOverridable = mOverridable;
    }
}
