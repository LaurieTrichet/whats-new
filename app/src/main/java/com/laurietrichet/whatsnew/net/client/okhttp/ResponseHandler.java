package com.laurietrichet.whatsnew.net.client.okhttp;

import com.laurietrichet.whatsnew.core.bus.BusProvider;
import com.laurietrichet.whatsnew.core.bus.HTTPError;
import com.laurietrichet.whatsnew.core.bus.NetworkError;
import com.laurietrichet.whatsnew.core.bus.ParsingError;
import com.laurietrichet.whatsnew.core.bus.UnexpectedError;


/**
 * Created by laurie on 18/07/15.
 */
public class ResponseHandler {

    public static void postNetworkError(String localizedMessage) {
        BusProvider.postOnUiThread(new NetworkError(""));
    }

    public static void postParsingError(String localizedMessage) {
        BusProvider.postOnUiThread(new ParsingError(""));
    }

    public static void postHttpError(String requestUrl, String message, int code) {
        BusProvider.postOnUiThread(new HTTPError(requestUrl, message, code));
    }

    public static void postUnexpectedError () {
        BusProvider.postOnUiThread(new UnexpectedError());
    }
}
