package com.laurietrichet.whatsnew.database;

import android.net.Uri;

/**
 * Contract for the database. Specify the schema of the database.
 */
public final class WNContract {

    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "com.laurietrichet.whatsnew.contentprovider";
    public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);

    static final String TEXT_TYPE = " TEXT";
    static final String INTEGER_TYPE = " INTEGER";
    static final String COMMA_SEP = ",";
    static final String NOT_NULL = " NOT NULL";
    static final String AUTOINCREMENT = " AUTOINCREMENT";

    static final String COLUMN_NAME_TITLE = "title";
    static final String COLUMN_NAME_LINK = "link";
    static final String COLUMN_NAME_DESCRIPTION = "description";

    static final String [] TABLES = {
            FeedTable.TABLE_NAME,
            ItemTable.TABLE_NAME
    };

}
