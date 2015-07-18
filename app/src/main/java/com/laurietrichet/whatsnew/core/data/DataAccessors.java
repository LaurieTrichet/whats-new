package com.laurietrichet.whatsnew.core.data;

import android.content.Context;

import java.util.EnumMap;

/**
 * Utility class to manage data
 * Controls the data accessors's creation.
 * See {@link com.laurietrichet.whatsnew.core.data.IDataAccessor} to know how access to data.
 */
public final class DataAccessors {

    public enum dataAccessorsKey { FEED_DATA_ACCESSOR, ITEM_DATA_ACCESSOR}

    private static final EnumMap<dataAccessorsKey,IDataAccessor> mAccessorList =
            new EnumMap<>(dataAccessorsKey.class);

    private DataAccessors ()throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Forbidden constructor");
    }

    /**
     * Return a < ? implements {@link IDataAccessor}> for the Key given in parameter
     * @param context context to pass to the < ? implements {@link IDataAccessor}> for creation
     * @param key the key to get the < ? implements {@link IDataAccessor}>
     * @return < ? implements {@link IDataAccessor}> that access data
     */
    public static IDataAccessor getAccessor (Context context, dataAccessorsKey key){
        if (! mAccessorList.containsKey(key)){
            if (key == dataAccessorsKey.FEED_DATA_ACCESSOR){
                mAccessorList.put(dataAccessorsKey.FEED_DATA_ACCESSOR,
                        new FeedDataAccessor(context));
            } else {
                mAccessorList.put(dataAccessorsKey.ITEM_DATA_ACCESSOR,
                        new ItemDataAccessor(context));
            }
        }
        return mAccessorList.get(key);
    }
}
