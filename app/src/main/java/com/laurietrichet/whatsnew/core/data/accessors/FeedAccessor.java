package com.laurietrichet.whatsnew.core.data.accessors;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.laurietrichet.whatsnew.core.bus.BusProvider;
import com.laurietrichet.whatsnew.core.bus.ParsingError;
import com.laurietrichet.whatsnew.net.client.IWebServiceClient;
import com.laurietrichet.whatsnew.net.client.WebServiceClients;
import com.squareup.otto.Subscribe;

/**
 * Class to access feed data
 */
class FeedAccessor implements IDataAccessor{

    public static final String TAG = FeedAccessor.class.getName();

    /**package*/
    FeedAccessor(Context context) {
        BusProvider.INSTANCE.getBus().register(this);
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
        IWebServiceClient feedWebServiceClient = WebServiceClients.createClient();
        feedWebServiceClient.get(url);
    }

    /**
     * Call this method to retrieve a cursor containing all feeds from database.
     * @return
     */
    public Cursor requestFeeds (Context context){
        //TODO implement cursor loader
        return null;
    }

    @Subscribe public void parsingError (ParsingError parsingError){
        Log.d(TAG, "parsingError ");
    }

    @Subscribe public void networkError (ParsingError parsingError){
        Log.d(TAG, "networkError ");
    }
}
