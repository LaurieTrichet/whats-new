package com.laurietrichet.whatsnew.data;

import android.content.Context;

import com.laurietrichet.whatsnew.net.client.AbstractVolleyWebServiceClient;
import com.laurietrichet.whatsnew.net.client.FeedWebServiceClient;

/**
 * Class to access feed data
 */
class FeedDataAccessor implements IDataAccessor{

    private final AbstractVolleyWebServiceClient mClient ;

    /**package*/ FeedDataAccessor (Context context) {
        mClient = new FeedWebServiceClient(context.getApplicationContext());
    }

    @Override
    public void getAll(DataAccessorListener listener) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void get(String id, DataAccessorListener listener) {
        throw new UnsupportedOperationException();
    }
}
