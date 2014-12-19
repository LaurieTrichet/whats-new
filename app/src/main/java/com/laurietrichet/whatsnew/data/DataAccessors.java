package com.laurietrichet.whatsnew.data;

import android.content.Context;

import java.util.EnumMap;

/**
 * Utility class to manage data
 * Controls the data accessors's creation.
 * See {@link com.laurietrichet.whatsnew.data.IDataAccessor} to know how access to data.
 */
public final class DataAccessors {

    public static enum DATA_ACCESSORS_ENUM { FEED_DATA_ACCESSOR, ITEM_DATA_ACCESSOR};

    private static final EnumMap<DATA_ACCESSORS_ENUM,IDataAccessor> mAccessorList =
            new EnumMap<DATA_ACCESSORS_ENUM, IDataAccessor>(DATA_ACCESSORS_ENUM.class);

    private DataAccessors ()throws UnsupportedOperationException{
        throw new UnsupportedOperationException("Forbidden constructor");
    }

    /**
     * Return a < ? implements {@link IDataAccessor}> for the Key given in parameter
     * @param context context to pass to the < ? implements {@link IDataAccessor}> for creation
     * @param key the key to get the < ? implements {@link IDataAccessor}>
     * @return < ? implements {@link IDataAccessor}> that access data
     */
    public static IDataAccessor getAccessor (Context context, DATA_ACCESSORS_ENUM key){
        if (! mAccessorList.containsKey(key)){
            if (key == DATA_ACCESSORS_ENUM.FEED_DATA_ACCESSOR){
                mAccessorList.put(DATA_ACCESSORS_ENUM.FEED_DATA_ACCESSOR,
                        new FeedDataAccessor(context));
            } else {
                mAccessorList.put(DATA_ACCESSORS_ENUM.ITEM_DATA_ACCESSOR,
                        new ItemDataAccessor(context));
            }
        }
        return mAccessorList.get(key);
    }
}
