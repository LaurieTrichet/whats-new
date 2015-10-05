package com.laurietrichet.whatsnew.core.bus;

/**
 * Created by laurie on 23/07/15.
 * Represent an error due to network fault.
 */
public class NetworkError {
    private final String message;

    public NetworkError(String localizedMessage) {
        message = localizedMessage;
    }

    public String getMessage() {
        return message;
    }
}
