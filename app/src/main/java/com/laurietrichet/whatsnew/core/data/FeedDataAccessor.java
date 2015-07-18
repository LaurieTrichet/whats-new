package com.laurietrichet.whatsnew.core.data;

import android.content.Context;

/**
 * Class to access feed data
 */
class FeedDataAccessor implements IDataAccessor{

    /**package*/ FeedDataAccessor (Context context) {

    }

    @Override
    public void getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void get(String id) {
        throw new UnsupportedOperationException();
    }
}
