package com.laurietrichet.whatsnew.core.bus;

/**
 * Created by laurie on 23/07/15.
 * Describe a parsing error.
 */
public class ParsingError {

    String message;

    public ParsingError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
