package me.marcon.flaggr;

/**
 * User: mmarcon
 * Date: 14/03/2014
 * Time: 16:38
 */
public class FlagNotOverridableException extends RuntimeException {
    private int flagId;

    public FlagNotOverridableException(int flagId) {
        this.flagId = flagId;
    }

    @Override
    public String getMessage() {
        return "Flag with id " + flagId + " is not overridable";
    }
}