package com.laurietrichet.whatsnew.data;

import android.content.Context;

/**
 * Created by laurie on 03/12/14.
 */
public class ItemDataAccessor implements IDataAccessor{


    /**package*/ ItemDataAccessor (Context context) {
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
