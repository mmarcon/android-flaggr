package me.marcon.flaggr;

/**
 * User: mmarcon
 * Date: 14/03/2014
 * Time: 16:38
 */
public class FlagNotFoundException extends RuntimeException {
    private String stringIdentifier;

    public FlagNotFoundException(String stringIdentifier) {
        this.stringIdentifier = stringIdentifier;
    }

    @Override
    public String getMessage() {
        return "Flag " + stringIdentifier + " does not exist";
    }
}