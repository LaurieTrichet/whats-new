package com.laurietrichet.whatsnew.core.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Describe Item object in database.
 */
public final class ItemTable implements BaseColumns{

    static final String TABLE_NAME = "item";
    public static final String COLUMN_NAME_FEED_ID_FK = "feed_id";
    static final String GUID = "guid";

    public static final Uri CONTENT_URI = Uri.withAppendedPath(WNContract.CONTENT_URI, "items");

    static final String SQL_CREATE_ITEM =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + WNContract.INTEGER_TYPE+" PRIMARY KEY" + WNContract.COMMA_SEP +
                    COLUMN_NAME_FEED_ID_FK + WNContract.INTEGER_TYPE + WNContract.COMMA_SEP +
                    GUID + WNContract.TEXT_TYPE + WNContract.COMMA_SEP +
                    WNContract.COLUMN_NAME_TITLE + WNContract.TEXT_TYPE + WNContract.COMMA_SEP +
                    WNContract.COLUMN_NAME_LINK + WNContract.TEXT_TYPE + WNContract.COMMA_SEP +
                    WNContract.COLUMN_NAME_DESCRIPTION + WNContract.TEXT_TYPE + WNContract.COMMA_SEP +
                    "FOREIGN KEY ("+ COLUMN_NAME_FEED_ID_FK +") REFERENCES "+
                    FeedTable.TABLE_NAME+"("+ _ID +")"+
                    " );";

    static final String SQL_DELETE_ITEM =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    /**
     * Standard projection for the interesting columns of an item.
     */
    static final String [] READ_FEED_PROJECTION = {
            _ID,
            WNContract.COLUMN_NAME_TITLE,
            WNContract.COLUMN_NAME_LINK,
            WNContract.COLUMN_NAME_DESCRIPTION
    };

    static final String DEFAULT_SORT_ORDER = WNContract.COLUMN_NAME_TITLE +" ASC";

    static final String MIME_TYPE_DIR = "vnd.android.cursor.dir/vnd.com.laurietrichet.provider.Item";
    static final String MIME_TYPE_ITEM = "vnd.android.cursor.item/vnd.com.laurietrichet.provider.Item";

    static void onCreate (SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_ITEM);
    }

    static void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        Log.w(ItemTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL(SQL_DELETE_ITEM);
        onCreate(database);
    }

    /**
     * verify that all the fields are filled correctly, and that the mandatory fields are in the
     * content value given in argument
     * @param values
     * @return true if the argument is correct.
     */
    static boolean isValidForInsert (ContentValues values) {
        if (values.containsKey(WNContract.COLUMN_NAME_TITLE) == false) {
            return false;
        }
        // If the values map doesn't contain note text, sets the value to an empty string.
        if (values.containsKey(WNContract.COLUMN_NAME_LINK) == false) {
            return false;
        }

        if (values.containsKey(WNContract.COLUMN_NAME_DESCRIPTION) == false) {
            return false;
        }
        return true;
    }
}
