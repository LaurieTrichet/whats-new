package com.laurietrichet.whatsnew.core.data;

import android.content.Context;
import android.opengl.GLES10;
import android.util.Log;

import com.laurietrichet.whatsnew.core.bus.BusEvent;
import com.laurietrichet.whatsnew.core.bus.ParsingError;
import com.laurietrichet.whatsnew.net.client.retrofit.AWebServiceClientCreator;
import com.laurietrichet.whatsnew.net.client.retrofit.FeedWebServiceClient;
import com.squareup.otto.Subscribe;

/**
 * Class to access feed data
 */
class FeedAccessor implements IDataAccessor{

    public static final String TAG = FeedAccessor.class.getName();

    /**package*/
    FeedAccessor(Context context) {
        BusEvent.getBus().register(this);
    }

    @Override
    public void getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void get(String id) {
        throw new UnsupportedOperationException();
    }

    /**
     * Retreive content from the remote source.
     * @param url
     */
    public void getRemote (String url) {
        FeedWebServiceClient feedWebServiceClient = AWebServiceClientCreator.createClient(url, FeedWebServiceClient.class);
        feedWebServiceClient.get();
    }

    @Subscribe public void parsingError (ParsingError parsingError){
        Log.d(TAG, "parsingError ");
    }

    @Subscribe public void networkError (ParsingError parsingError){
        Log.d(TAG, "networkError ");
    }
}
