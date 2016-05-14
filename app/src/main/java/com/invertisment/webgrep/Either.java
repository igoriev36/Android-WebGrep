package com.invertisment.webgrep;

/**
 * Created on 5/14/16.
 *
 * @author invertisment
 */
class Either {
    private final String[] matches;
    private final String errorMessage;

    public Either(String errorMessage) {
        this.errorMessage = errorMessage;
        this.matches = null;
    }

    public Either(String[] matches) {
        this.matches = matches;
        this.errorMessage = "No error";
    }

    public String[] getMatches() {
        return matches;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isEmpty() {
        return matches == null;
    }
}

